package com.dani.kibernum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dani.kibernum.R
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.view.adapter.FeedListAdapter
import com.dani.kibernum.view.utils.action
import com.dani.kibernum.view.utils.snack
import com.dani.kibernum.viewmodel.AppResource
import com.dani.kibernum.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kibernum_activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), FeedsCallback {

    private val rvAdapter: FeedListAdapter = FeedListAdapter(this)
    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kibernum_activity_home)

        initRecyclerView()
        bringFeeds()

    }

    private fun initRecyclerView() {
        recyclerview.adapter = rvAdapter
    }


    private fun bringFeeds() {
        viewModel.getAllRepositoryList().observe(this,::onResponse)
    }

    private fun onResponse(resource: AppResource<List<FeedsItem>>) {
        when(resource) {
            is AppResource.Error -> showMessage(resource.message)
            is AppResource.Loading -> showLoading()
            is AppResource.Success -> showFeeds(resource.data)
        }
    }

    private fun showFeeds(data: List<FeedsItem>?) {
        hideLoading()
        data?.also {
            rvAdapter.setListData(it)
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
}

interface FeedsCallback {
    fun onFeedClicked(feed: FeedsItem): Unit
}


