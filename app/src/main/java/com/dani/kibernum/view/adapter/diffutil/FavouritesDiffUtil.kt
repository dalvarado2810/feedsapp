package com.dani.kibernum.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.dani.kibernum.data.model.FavouriteFeeds
import com.dani.kibernum.data.model.FeedsItem


object FavouritesDiffUtil : DiffUtil.ItemCallback<FavouriteFeeds>() {

    override fun areItemsTheSame(oldItem: FavouriteFeeds, newItem: FavouriteFeeds): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavouriteFeeds, newItem: FavouriteFeeds): Boolean {
        return oldItem == newItem
    }
}