package com.dani.kibernum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dani.kibernum.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteActivity : AppCompatActivity() {

    private val navigatemenu = BottomNavigationView.OnNavigationItemSelectedListener {
            item ->
        when (item.itemId) {
            R.id.home -> {
                val intent = Intent(this@FavoriteActivity, HomeActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorites -> {

                return@OnNavigationItemSelectedListener false
            }

        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_activity_favorite)


        val navigationBar = findViewById<BottomNavigationView>(
            R.id.navigationmenufav)
        navigationBar.selectedItemId = R.id.favorites
        navigationBar.setOnNavigationItemSelectedListener(navigatemenu)


    }
}