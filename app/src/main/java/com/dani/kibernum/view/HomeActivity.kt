package com.dani.kibernum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dani.kibernum.R
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FavouriteFeeds
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.model.relations.ContactsAndFeeds
import com.dani.kibernum.view.adapter.FeedListAdapter
import com.dani.kibernum.view.utils.action
import com.dani.kibernum.view.utils.snack
import com.dani.kibernum.viewmodel.AppResource
import com.dani.kibernum.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kibernum_activity_home.*
import kotlinx.android.synthetic.main.kibernum_detail_activity.*
import java.io.Serializable

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), FeedsCallback {

    private val rvAdapter: FeedListAdapter = FeedListAdapter(this)
    val viewModel: HomeViewModel by viewModels()
    var favoriteFeeds: List<FavouriteFeeds> = emptyList()



    private val navigatemenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
        bringFavourites()

        val navigationBar = findViewById<BottomNavigationView>(
            R.id.navigationmenu
        )
        navigationBar.selectedItemId = R.id.home
        navigationBar.setOnNavigationItemSelectedListener(navigatemenu)

    }

    private fun initRecyclerView() {
        recyclerview.adapter = rvAdapter
    }

    private fun bringFeeds() {
        viewModel.getAllRepositoryList().observe(this, ::onResponse)
        viewModel.getAllContactsList().observe(this, ::onResponseC)
    }

    private fun onResponse(resource: AppResource<List<FeedsItem>>) {
        when (resource) {
            is AppResource.Error -> showMessage(resource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> showFeeds(resource.data)
        }
    }


    private fun onResponseC(resource: AppResource<List<ContactsItem>>) {
        when (resource) {
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
        val isfav = favouriteChecker(feed.id)
        val contactsAndFeeds = getContactsAndFeeds(feed.author_id!!)


        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra("feed", feed).putExtra("isFavourite", isfav).
            putExtra("array", contactsAndFeeds as Serializable)
        })
        finish()
    }
    //chequeo contactos

    private fun getContactsAndFeeds(author: String) :List<ContactsAndFeeds> {
        val contacts = viewModel.getContactsAndFeeds(author)
        return contacts}


    //chequeo favoritos

    private fun bringFavourites() {
        viewModel.getAllFavouritesList().observe(this, ::onResponseF)
    }

    private fun onResponseF(resource: AppResource<List<FavouriteFeeds>>) {
        when (resource) {
            is AppResource.Error -> showMessage(resource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> resource.data?.let { showFavs(it) }
        }
    }

    private fun showFavs(data: List<FavouriteFeeds>) {

        favoriteFeeds = data

    }

    fun favouriteChecker(id: Int?): Boolean {
        var isFavourite: Boolean = false
        favoriteFeeds.forEach { favoriteFeeds ->
            when (id) {
                favoriteFeeds.id -> isFavourite = true
            }
        }
        return isFavourite
    }


}

interface FeedsCallback {
    fun onFeedClicked(feed: FeedsItem): Unit

}


