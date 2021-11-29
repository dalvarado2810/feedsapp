package com.dani.kibernum.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.NonNull
import com.dani.kibernum.R
import com.dani.kibernum.data.db.ContactsDao
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.view.adapter.FeedListAdapter
import com.dani.kibernum.view.utils.action
import com.dani.kibernum.view.utils.snack
import com.dani.kibernum.viewmodel.AppResource
import com.dani.kibernum.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kibernum_activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), FeedsCallback, FavoriteFeed {

    private val rvAdapter: FeedListAdapter = FeedListAdapter(this, this)
    val viewModel: HomeViewModel by viewModels()

    private val navigatemenu = BottomNavigationView.OnNavigationItemSelectedListener {
            item ->
        when (item.itemId) {
            R.id.home -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.favorites -> {
                val intent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_activity_home)

        initRecyclerView()
        bringFeeds()


        val navigationBar = findViewById<BottomNavigationView>(
            R.id.navigationmenu)
        navigationBar.selectedItemId = R.id.home
        navigationBar.setOnNavigationItemSelectedListener(navigatemenu)

    }

    private fun initRecyclerView() {
        recyclerview.adapter = rvAdapter
    }


    private fun bringFeeds() {
        viewModel.getAllRepositoryList().observe(this,::onResponse)
        viewModel.getAllContactsList().observe(this,::onResponseC)

    }

    private fun onResponse(resource: AppResource<List<FeedsItem>>) {
        when(resource) {
            is AppResource.Error -> showMessage(resource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> showFeeds(resource.data)
        }
    }

    private fun onResponseC(resource: AppResource<List<ContactsItem>>) {
        when(resource) {
            is AppResource.Error -> showMessage(resource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> hideLoading()
        }
    }

    private fun showFeeds(data: List<FeedsItem>?) {
        hideLoading()
        data?.also {
            rvAdapter.submitList(it)
        }
    }



    private fun showLoading() {
        homeProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        homeProgressBar.visibility = View.GONE
    }

    private fun showMessage(message: String?) {
        hideLoading()
        message?.also {
            container.snack(it, Snackbar.LENGTH_INDEFINITE) {
                action(getString(R.string.kibernum_ok)) {
                }
            }
        }
    }

    override fun onFeedClicked(feed: FeedsItem) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra("feed", feed)
        })
    }

    override fun onFavClicked(feed: FeedsItem){

    }
}



interface FeedsCallback {
    fun onFeedClicked(feed: FeedsItem): Unit
}

interface FavoriteFeed {
    fun onFavClicked(feed : FeedsItem): Unit
}


