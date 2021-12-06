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
import com.dani.kibernum.view.adapter.FavouritesListAdapter
import com.dani.kibernum.view.adapter.FeedListAdapter
import com.dani.kibernum.view.utils.action
import com.dani.kibernum.view.utils.snack
import com.dani.kibernum.viewmodel.AppResource
import com.dani.kibernum.viewmodel.FavoriteViewModel
import com.dani.kibernum.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kibernum_activity_home.*

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private val rvAdapter: FavouritesListAdapter = FavouritesListAdapter()
    val favoriteViewModel: FavoriteViewModel by viewModels()
    var favoriteFeeds : List<FavouriteFeeds> = emptyList()


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
        setContentView(R.layout.kibernum_activity_home)

        initRecyclerView()
        bringFavourites()

        val navigationBar = findViewById<BottomNavigationView>(
            R.id.navigationmenu)
        navigationBar.selectedItemId = R.id.home
        navigationBar.setOnNavigationItemSelectedListener(navigatemenu)

    }

    private fun initRecyclerView() {
        recyclerview.adapter = rvAdapter
    }


    private fun showFavourites(data: List<FavouriteFeeds>?) {
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

    //chequeo favoritos

    private fun bringFavourites(){
        favoriteViewModel.getAllFavouritesList().observe(this,::onResponseF)
    }

    private fun onResponseF(resource : AppResource<List<FavouriteFeeds>>){
        when(resource) {
            is AppResource.Error -> showMessage(resource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> showFavourites(resource.data)
        }
    }


    fun favouriteChecker(id: Int?):Boolean{
        var isFavourite : Boolean = false
        favoriteFeeds.forEach { favoriteFeeds ->
            when (id) {
                favoriteFeeds.id -> isFavourite = true
            }
        }
        return isFavourite
    }



}




