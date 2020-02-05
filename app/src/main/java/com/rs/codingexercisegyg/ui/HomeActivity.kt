package com.rs.codingexercisegyg.ui

import android.os.Bundle
import com.rs.codingexercisegyg.R
import com.rs.codingexercisegyg.base.BaseActivity
import com.rs.codingexercisegyg.reviewslist.ReviewsListFragment

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setToolBarTitle()
        startReviewsListFragment()
    }

    private fun setToolBarTitle() {
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun startReviewsListFragment() {
        val fragment =
            supportFragmentManager.findFragmentByTag(ReviewsListFragment::class.java.name)
                ?: ReviewsListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(
            R.id.frame_layout,
            fragment,
            ReviewsListFragment::class.java.name
        ).commit()
    }
}