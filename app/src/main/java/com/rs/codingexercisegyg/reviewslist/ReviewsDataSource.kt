package com.rs.codingexercisegyg.reviewslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.rs.codingexercisegyg.network.GYGReviewsApi
import com.rs.codingexercisegyg.network.data.Review
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsDataSource @Inject
constructor(
    private val gygReviewsApi: GYGReviewsApi
) : ItemKeyedDataSource<Int, Review>() {

    companion object {
        private const val TAG = "ReviewsDataSource"
    }

    private var offset = 0
    private val paginatedNetworkStateLiveData: MutableLiveData<NetworkState> = MutableLiveData()
    private val initialLoadStateLiveData: MutableLiveData<NetworkState> = MutableLiveData()
    // For Retry
    private lateinit var params: LoadParams<Int>
    private lateinit var callback: LoadCallback<Review>
    private lateinit var job: Job

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Review>
    ) {
        Log.d(TAG, "Fetching first page: $offset")
        initialLoadStateLiveData.postValue(Loading)
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            initialLoadStateLiveData.postValue(NetworkError(exception.message))
            Log.e(TAG, exception.localizedMessage, exception)
        }
        job = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            val reviews = gygReviewsApi.getReviews(offset = offset)
            withContext(Dispatchers.Main) {
                onReviewsFetched(reviews.reviews, callback)
            }
        }
    }

    private fun onReviewsFetched(
        reviews: List<Review>,
        callback: LoadInitialCallback<Review>
    ) {
        initialLoadStateLiveData.postValue(Success)
        offset++
        callback.onResult(reviews)
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Review>
    ) {
        this.params = params
        this.callback = callback
        Log.d(TAG, "Fetching next page: $offset")
        paginatedNetworkStateLiveData.postValue(Loading)
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            paginatedNetworkStateLiveData.postValue(NetworkError(exception.message))
            Log.e(TAG, exception.localizedMessage, exception)
        }
        job = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            val reviews = gygReviewsApi.getReviews(offset = params.key)
            withContext(Dispatchers.Main) {
                onMoreReviewsFetched(reviews.reviews, callback)
            }
        }
    }

    private fun onMoreReviewsFetched(reviews: List<Review>, callback: LoadCallback<Review>) {
        paginatedNetworkStateLiveData.postValue(Success)
        offset++
        callback.onResult(reviews)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Review>
    ) {
        // Do nothing, since data is loaded from our initial load itself
    }

    override fun getKey(item: Review): Int {
        return offset
    }

    fun clear() {
        offset = 0
        job.cancel()
    }

    fun getPaginatedNetworkStateLiveData(): LiveData<NetworkState> {
        return paginatedNetworkStateLiveData
    }

    fun getInitialLoadStateLiveData(): LiveData<NetworkState> {
        return initialLoadStateLiveData
    }

    fun retryPagination() {
        loadAfter(params, callback)
    }
}
