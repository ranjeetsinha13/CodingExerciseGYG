package com.rs.codingexercisegyg.reviewslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rs.codingexercisegyg.R
import com.rs.codingexercisegyg.network.data.Review
import com.rs.codingexercisegyg.utils.convertStringDateFormat
import com.rs.codingexercisegyg.utils.loadImage
import kotlinx.android.synthetic.main.loading_list_item.view.*
import kotlinx.android.synthetic.main.network_failure_list_item.view.*
import kotlinx.android.synthetic.main.reviews_list_item.view.*

enum class ViewType(val value: Int) {
    LOADING(1),
    ERROR(2),
    SUCCESS(3)
}

class ReviewListPagedAdaptor constructor(
    diffCallback: DiffUtil.ItemCallback<Review>,
    private val callback: Callback,
    private val itemClickCallback: ReviewItemClickCallback
) : PagedListAdapter<Review, RecyclerView.ViewHolder>(diffCallback) {
    private lateinit var context: Context
    private var currentNetworkState: NetworkState = Loading

    // Add an item for the network state
    private val extraRow: Int
        get() = if (hasExtraRow())
            1
        else
            0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        when (viewType) {
            ViewType.LOADING.value -> {
                return LoadingHolder(
                    layoutInflater.inflate(
                        R.layout.loading_list_item,
                        parent,
                        false
                    ) as FrameLayout
                )
            }
            ViewType.SUCCESS.value -> {
                val reviewView =
                    layoutInflater.inflate(
                        R.layout.reviews_list_item,
                        parent,
                        false
                    ) as FrameLayout
                return ReviewsViewHolder(reviewView)
            }
            else -> {
                val networkFailureHolder = NetworkFailureHolder(
                    layoutInflater.inflate(
                        R.layout.network_failure_list_item,
                        parent,
                        false
                    ) as FrameLayout
                )
                networkFailureHolder.view.retry.setOnClickListener { callback.onRetryClicked() }
                return networkFailureHolder
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ViewType.LOADING.value -> {
                val loadingHolder = holder as LoadingHolder
                loadingHolder.view.spinner.visibility = View.VISIBLE
            }
            ViewType.ERROR.value -> {
                val networkHolder = holder as NetworkFailureHolder
                networkHolder.bind(currentNetworkState as NetworkError)
            }
            ViewType.SUCCESS.value -> {
                val review = getItem(position)
                val reviewHolder = holder as ReviewsViewHolder

                reviewHolder.view.setOnClickListener { itemClickCallback.onItemClicked(review) }
                reviewHolder.bind(review)
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousNetworkState = currentNetworkState
        val hadExtraRow = hasExtraRow()
        currentNetworkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousNetworkState != newNetworkState) {

            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + extraRow
    }

    override fun getItemViewType(position: Int): Int {
        // Reached at the end
        return if (hasExtraRow() && position == itemCount - 1) {
            if (currentNetworkState === Loading) {
                ViewType.LOADING.value
            } else {
                ViewType.ERROR.value
            }
        } else {
            ViewType.SUCCESS.value
        }
    }

    private fun hasExtraRow(): Boolean {
        return currentNetworkState != Success
    }

    class ReviewsViewHolder(val view: FrameLayout) : RecyclerView.ViewHolder(view) {
        fun bind(review: Review?) {

            view.author_name.text = String.format(
                view.resources.getString(R.string.reviewer_name),
                review?.author?.fullName ?: "",
                review?.author?.country ?: ""
            )
            view.message.text = review?.message ?: ""
            view.rating.text = String.format(
                view.resources.getString(R.string.rating), review?.rating.toString()
            )
            review?.created?.let {
                view.date.text = convertStringDateFormat(it)
            }
            review?.author?.photoUrl?.loadImage(view.reviewer_image)
        }
    }

    class LoadingHolder(val view: FrameLayout) : RecyclerView.ViewHolder(view)

    class NetworkFailureHolder(val view: FrameLayout) :
        RecyclerView.ViewHolder(view) {
        fun bind(networkError: NetworkError) {
            view.visibility = View.VISIBLE
            view.network_error.text = networkError.message
        }
    }

    interface Callback {
        fun onRetryClicked()
    }

    interface ReviewItemClickCallback {
        fun onItemClicked(review: Review?)
    }
}