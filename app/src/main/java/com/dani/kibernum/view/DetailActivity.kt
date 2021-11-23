package com.dani.kibernum.view

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.dani.kibernum.R
import com.dani.kibernum.data.model.FeedsItem
import kotlinx.android.synthetic.main.kibernum_detail_activity.*

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_detail_activity)
        initData()
    }

    private fun initData() {
        val feed = intent.getSerializableExtra("feed") as FeedsItem
        tv_desc.text = Html.fromHtml(feed.description, Html.FROM_HTML_MODE_COMPACT)
        tv_tittle.text = feed.title
        iv1.load(feed.image){
            crossfade(true)
            placeholder(R.drawable.image)
        }
        tv_published.text = feed.published
        tv_author.text = feed.author_id
    }
}