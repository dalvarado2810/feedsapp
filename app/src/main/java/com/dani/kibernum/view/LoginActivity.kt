package com.dani.kibernum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dani.kibernum.R
import com.dani.kibernum.data.model.LoginDTO
import com.dani.kibernum.data.model.LoginResponse
import com.dani.kibernum.view.utils.action
import com.dani.kibernum.view.utils.snack
import com.dani.kibernum.viewmodel.AppResource
import com.dani.kibernum.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kibernum_login_activity.*

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_login_activity)


        login_button.setOnClickListener {

            loginViewModel.doLogin(
                LoginDTO(
                    username = username.text.toString(),
                    password = password.text.toString()
                )
            ).observe(this, Observer(::handleResult))

        }
    }

    private fun handleResult(appResource: AppResource<LoginResponse>) {
        hideLoading()
        when(appResource) {
            is AppResource.Error -> showMessage(appResource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> goToHome()
        }

    }

    private fun goToHome() {
        if(loginViewModel != null) {
            val intentHome = Intent(this,HomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showMessage(message: String?) {
        message?.also {
            listLayout.snack(it, Snackbar.LENGTH_INDEFINITE) {
                action(getString(R.string.kibernum_ok)) {

                }
            }
        }
    }
}