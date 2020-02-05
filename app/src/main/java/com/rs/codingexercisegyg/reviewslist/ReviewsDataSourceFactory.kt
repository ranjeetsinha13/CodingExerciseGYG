package com.rs.codingexercisegyg.reviewslist

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rs.codingexercisegyg.network.data.Review
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsDataSourceFactory @Inject
constructor(val reviewsDataSource: ReviewsDataSource) : DataSource.Factory<Int, Review>() {
    private val showsDataSourceLiveData: MutableLiveData<ReviewsDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Review> {
        showsDataSourceLiveData.postValue(reviewsDataSource)
        return reviewsDataSource
    }
}