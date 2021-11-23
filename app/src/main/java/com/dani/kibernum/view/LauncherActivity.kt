package com.dani.kibernum.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dani.kibernum.R
import com.dani.kibernum.viewmodel.LauncherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LauncherActivity: AppCompatActivity() {

    val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_launcher)
        askIfUserIsLogged()
    }

    private fun askIfUserIsLogged() {
        if (viewModel.isLogged()) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
     }
}