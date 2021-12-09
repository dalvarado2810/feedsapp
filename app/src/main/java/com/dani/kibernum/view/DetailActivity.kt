package com.dani.kibernum.view

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import coil.load
import com.dani.kibernum.R
import com.dani.kibernum.data.db.ContactsDao
import com.dani.kibernum.data.model.*
import com.dani.kibernum.data.model.relations.ContactsAndFeeds
import com.dani.kibernum.view.utils.action
import com.dani.kibernum.view.utils.snack
import com.dani.kibernum.viewmodel.AppResource
import com.dani.kibernum.viewmodel.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kibernum_detail_activity.*
import kotlinx.android.synthetic.main.kibernum_login_activity.*

@AndroidEntryPoint
class DetailActivity: AppCompatActivity() {

    private val favoriteViewModel : FavoriteViewModel by viewModels()
    var favoriteFeeds : List<FavouriteFeeds> = emptyList()
    var isFav : Boolean = false
    var contactsAndFeeds : List<ContactsAndFeeds> = emptyList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_detail_activity)

        initFav()
        initData()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,HomeActivity::class.java))
    }


    fun initFav(){
        val isFavourite = intent.getSerializableExtra("isFavourite") as Boolean
        isFav = isFavourite
        if (isFavourite) {
            favBtn.setImageResource(R.drawable.ic_favorite)
        }
    }


     fun initData() {
         val feed = intent.getSerializableExtra("feed") as FeedsItem
         val contact = intent.getSerializableExtra("array") as MutableList<ContactsAndFeeds>
        tv_desc.text = Html.fromHtml(feed?.description, Html.FROM_HTML_MODE_COMPACT)
        tv_tittle.text = feed?.title
        iv1.load(feed?.image){
            crossfade(true)
            placeholder(R.drawable.image)
        }
        tv_published.text = feed?.published
        tv_author.text = "${contact[0].contacts.firstName} , ${contact[0].contacts.lastName}"

        favBtn.setOnClickListener {

            favoriteViewModel.markAsFavourite(FavoriteDto(postid = feed?.id)
            ).observe(this, Observer(::handleResult))

        }

    }

    private fun handleResult(appResource: AppResource<FavoriteResponse?>) {
        hideLoading()
        when(appResource) {
            is AppResource.Error -> showMessage(appResource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> favouriteResult()
        }

    }

    private fun favouriteResult(){
        val feeds = intent.getSerializableExtra("feed") as FeedsItem
        val contact = intent.getSerializableExtra("array") as MutableList<ContactsAndFeeds>
        if (isFav){
            val favourite = FavouriteFeeds(feeds.id,
                feeds.title,
                feeds.description,
                feeds.image,
                feeds.date,
                feeds.author_id,
                contact[0].contacts.firstName,
                contact[0].contacts.lastName)
            favoriteViewModel.deleteFavs(favourite)
            isFav = false

            favBtn.setImageResource(R.drawable.ic_favorite_outline)
        }else {
            val favourites = FavouriteFeeds(feeds.id,
                feeds.title,
                feeds.description,
                feeds.image,
                feeds.date,
                feeds.author_id,
                contact[0].contacts.firstName,
                contact[0].contacts.lastName)
            favoriteViewModel.insertFavs(favourites)
            favBtn.setImageResource(R.drawable.ic_favorite)
            isFav = true

        }
    }

    private fun showLoading() {
        progressFavBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressFavBar.visibility = View.GONE
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