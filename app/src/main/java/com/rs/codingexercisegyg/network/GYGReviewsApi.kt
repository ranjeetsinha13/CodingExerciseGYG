package com.rs.codingexercisegyg.network

import com.rs.codingexercisegyg.network.data.Reviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GYGReviewsApi {

    @GET("/activities/{activityID}/reviews")
    suspend fun getReviews(
        @Path("activityID") activityID: Int = 23776,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int
    ): Reviews
}