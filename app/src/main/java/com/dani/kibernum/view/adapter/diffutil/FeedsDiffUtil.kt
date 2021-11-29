package com.dani.kibernum.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.dani.kibernum.data.model.FeedsItem

object FeedsDiffUtil : DiffUtil.ItemCallback<FeedsItem>() {

    override fun areItemsTheSame(oldItem: FeedsItem, newItem: FeedsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedsItem, newItem: FeedsItem): Boolean {
        return oldItem == newItem
    }
}