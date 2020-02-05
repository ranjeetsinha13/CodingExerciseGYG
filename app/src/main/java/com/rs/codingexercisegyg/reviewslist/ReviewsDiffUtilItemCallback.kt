package com.rs.codingexercisegyg.reviewslist

import androidx.recyclerview.widget.DiffUtil
import com.rs.codingexercisegyg.network.data.Review

class ReviewsDiffUtilItemCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.message == newItem.message
    }
}