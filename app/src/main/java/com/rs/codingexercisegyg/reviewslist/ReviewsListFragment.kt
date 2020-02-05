package com.rs.codingexercisegyg.reviewslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rs.codingexercisegyg.R
import com.rs.codingexercisegyg.di.Injectable
import com.rs.codingexercisegyg.network.data.Review
import com.rs.codingexercisegyg.ui.ReviewDetailsActivity
import kotlinx.android.synthetic.main.network_failure_list_item.*
import kotlinx.android.synthetic.main.reviews_list_fragment.*
import javax.inject.Inject

class ReviewsListFragment : Fragment(), Injectable, ReviewListPagedAdaptor.Callback,
    ReviewListPagedAdaptor.ReviewItemClickCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val reviewsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ReviewsListViewModel::class.java]
    }

    private lateinit var reviewListPagedAdaptor: ReviewListPagedAdaptor

    companion object {
        fun newInstance() = ReviewsListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reviews_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
    }

    private fun handleUI() {
        swipe_refresh.setOnRefreshListener {
            reviewsViewModel.retry()
            swipe_refresh.isRefreshing = false
        }
        reviewsViewModel.initialLoadState()
            .observe(viewLifecycleOwner, Observer { setProgress(it) })
        reviewsViewModel.onScreenCreated()
        initAdapter()
    }

    private fun setProgress(loadState: NetworkState) {
        when (loadState) {
            is Success -> progress.visibility = View.GONE
            is NetworkError -> {
                progress.visibility = View.GONE
                handleNetworkError(loadState.message ?: getString(R.string.generic_error))
            }
            is Loading -> progress.visibility = View.VISIBLE
        }
    }

    private fun handleNetworkError(message: String) {
        network_error_view.visibility = View.VISIBLE
        network_error.text = message
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        reviewListPagedAdaptor = ReviewListPagedAdaptor(ReviewsDiffUtilItemCallback(), this, this)
        reviews.layoutManager = layoutManager
        reviews.adapter = reviewListPagedAdaptor
        reviewsViewModel.getReviews().observe(viewLifecycleOwner, Observer { showAllReviews(it) })
        reviewsViewModel.paginatedLoadState()
            .observe(this, Observer {
                setAdapterState(it)
            })
    }

    private fun setAdapterState(networkState: NetworkState) {
        reviewListPagedAdaptor.setNetworkState(networkState)
    }

    private fun showAllReviews(reviewsList: PagedList<Review>?) {
        reviewListPagedAdaptor.submitList(reviewsList)
        reviews.visibility = View.VISIBLE
    }

    override fun onRetryClicked() {
        reviewsViewModel.retry()
    }

    override fun onItemClicked(review: Review?) {
        val intent = Intent(requireActivity(), ReviewDetailsActivity::class.java).apply {
            putExtra(ReviewDetailsActivity.REVIEW_DATA, review)
        }
        startActivity(intent)
    }
}