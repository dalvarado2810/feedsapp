package com.dani.kibernum.view.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dani.kibernum.R
import com.dani.kibernum.data.model.FavouriteFeeds
import com.dani.kibernum.view.adapter.diffutil.FavouritesDiffUtil
import kotlinx.android.synthetic.main.kibernum_feed_item.view.*

class FavouritesListAdapter() :
    ListAdapter<FavouriteFeeds,
            FavouritesListAdapter.FeedViewHolder>(
            FavouritesDiffUtil){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.kibernum_favourites_item,
                parent,
                false)

        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    inner class FeedViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {

        private val imageFeed = view.iv1
        private val tittleFeed = view.tv_tittle
        private val descFeed = view.tv_desc
        private val authorFeed = view.nametv


        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(feed : FavouriteFeeds) {
            authorFeed.text = feed.author_id
            tittleFeed.text = feed.title
            descFeed.text = Html.fromHtml(feed.description, Html.FROM_HTML_MODE_COMPACT)
            imageFeed.load(feed.image){
                crossfade(true)
                placeholder(R.drawable.image)
            }




        }

    }

}