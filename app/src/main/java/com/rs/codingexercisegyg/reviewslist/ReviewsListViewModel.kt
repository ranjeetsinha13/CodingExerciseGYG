package com.rs.codingexercisegyg.reviewslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rs.codingexercisegyg.network.data.Review
import javax.inject.Inject

class ReviewsListViewModel @Inject
constructor(private val reviewsDataSourceFactory: ReviewsDataSourceFactory) : ViewModel() {

    private lateinit var reviews: LiveData<PagedList<Review>>

    companion object {
        private const val PAGE_SIZE = 4
        private const val INITIAL_LOAD_SIZE = 100
    }

    fun onScreenCreated() {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .build()
        reviews = LivePagedListBuilder(reviewsDataSourceFactory, config).build()
    }

    fun getReviews(): LiveData<PagedList<Review>> {
        return reviews
    }

    fun initialLoadState(): LiveData<NetworkState> {
        return reviewsDataSourceFactory.reviewsDataSource.getInitialLoadStateLiveData()
    }

    fun paginatedLoadState(): LiveData<NetworkState> {
        return reviewsDataSourceFactory.reviewsDataSource.getPaginatedNetworkStateLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        reviewsDataSourceFactory.reviewsDataSource.clear()
    }

    fun retry() {
        reviewsDataSourceFactory.reviewsDataSource.retryPagination()
    }
}