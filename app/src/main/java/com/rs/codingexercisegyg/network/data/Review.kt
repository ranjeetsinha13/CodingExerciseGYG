package com.rs.codingexercisegyg.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Reviews(
    val reviews: List<Review>,
    val totalCount: Int,
    val averageRating: Double,
    val pagination: Pagination
)

data class Pagination(
    val limit: Int,
    val offset: Int
)

@Parcelize
data class Review(
    val id: Long,
    val author: Author?,
    val title: String?,
    val message: String,
    val enjoyment: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val created: String,
    val language: String,
    val travelerType: String?
) : Parcelable

@Parcelize
data class Author(
    val fullName: String?,
    val country: String?,
    @SerializedName("photo")
    val photoUrl: String?
) : Parcelable