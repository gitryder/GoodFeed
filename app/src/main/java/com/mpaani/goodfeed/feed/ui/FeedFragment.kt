package com.mpaani.goodfeed.feed.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.ui.BaseFragment
import com.mpaani.goodfeed.feed.FeedPresenterContract
import com.mpaani.goodfeed.feed.FeedViewContract
import com.mpaani.goodfeed.feed.adapter.FeedAdapter
import com.mpaani.goodfeed.feed.viewmodel.FeedViewCache
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel
import com.mpaani.goodfeed.post.ui.PostActivity
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : BaseFragment(), FeedViewContract, FeedAdapter.FeedListener {

    companion object {
        const val TAG = "FeedFragment"

        private const val FEED_SCROLL_STATE = "feed_scroll_state"
    }

    override val fragmentTag = TAG
    override val layout = R.layout.fragment_feed
    override lateinit var refreshIndicator: SwipeRefreshLayout

    private lateinit var feedPresenter: FeedPresenterContract
    private lateinit var feedViewCache: FeedViewCache
    private val feedAdapter = FeedAdapter(this)

    private var savedScrollState: Parcelable? = null

    /**
     * Set this fragment's presenter.
     */
    fun setFeedPresenter(feedPresenterContract: FeedPresenterContract) {
        this.feedPresenter = feedPresenterContract
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseCache()
        initViews()
        fetchData()
    }

    override fun onStop() {
        feedPresenter.onExit()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(FEED_SCROLL_STATE, feed_recyclerview.layoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedScrollState = savedInstanceState.getParcelable(FEED_SCROLL_STATE)
    }

    override fun onFeedItemClicked(feedViewModel: FeedViewModel) {
        postClicked(feedViewModel)
    }

    override fun onFeedItemsReceived(feedModels: List<FeedViewModel>) {
        populateFeed(feedModels)
        cacheViewModels(feedModels)
    }

    private fun populateFeed(feedModels: List<FeedViewModel>) {
        stopLoadingIndicator()
        feedAdapter.setItems(feedModels)
        savedScrollState?.let {
            feed_recyclerview.layoutManager.onRestoreInstanceState(savedScrollState)
            savedScrollState = null
        }
    }

    override fun onNavigateToPost(postId: Int, userName: String, userEmail: String) {
        stopLoadingIndicator()
        PostActivity.startActivity(context!!, postId, userName, userEmail)
    }

    override fun onError(reason: String) {
        stopLoadingIndicator()
        Snackbar.make(refreshIndicator, reason, Snackbar.LENGTH_LONG).show()
    }

    private fun initViews() {
        initSwipeRefresh()
        initRecyclerView()
    }

    private fun initSwipeRefresh() {
        refreshIndicator = feed_swipe_refresh
        refreshIndicator.setOnRefreshListener { feedPresenter.forceRefreshItems() }
        startLoadingIndicator()
    }

    private fun initRecyclerView() {
        feed_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = feedAdapter
        }
    }

    private fun fetchData() {
        if (feedViewCache.hasData()) populateFeed(feedViewCache.getItems()!!)
        else feedPresenter.fetchItems()
    }

    private fun initialiseCache() {
        feedViewCache = ViewModelProviders.of(this).get(FeedViewCache::class.java)
    }

    private fun cacheViewModels(feedModels: List<FeedViewModel>) {
        feedViewCache.cacheItems(feedModels)
    }

    private fun postClicked(feedViewModel: FeedViewModel) {
        feedPresenter.postClicked(feedViewModel)
    }
}
