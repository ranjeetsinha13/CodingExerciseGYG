package com.rs.codingexercisegyg.ui

import android.os.Bundle
import android.os.PersistableBundle
import com.rs.codingexercisegyg.R
import com.rs.codingexercisegyg.base.BaseActivity
import com.rs.codingexercisegyg.network.data.Review
import com.rs.codingexercisegyg.utils.convertStringDateFormat
import com.rs.codingexercisegyg.utils.loadImage
import kotlinx.android.synthetic.main.review_details_activity.*

class ReviewDetailsActivity : BaseActivity() {

    companion object {
        const val REVIEW_DATA = " review"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_details_activity)

        if (savedInstanceState == null) {
            handleUI(intent.getParcelableExtra(REVIEW_DATA) as Review)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(REVIEW_DATA, intent.getParcelableExtra(REVIEW_DATA))
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        handleUI(savedInstanceState.getParcelable<Review>(REVIEW_DATA) as Review)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun handleUI(review: Review) {
        setToolbar()
        review.let {
            message.text = it.message
            reviewer_name.text = String.format(
                    resources.getString(R.string.reviewer_name),
                    it.author?.fullName ?: "",
                    it.author?.country ?: ""
            )
            rating.text = String.format(
                    resources.getString(R.string.rating), it.rating.toString()
            )

            it.created.let {
                date.text = convertStringDateFormat(it)
            }
            it.author?.photoUrl?.loadImage(reviewer_image)
            traveller_type.text =
                    String.format(resources.getString(R.string.traveller_type, it.travelerType))
        }
    }

    private fun setToolbar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.review_details)
    }
}