package com.rs.codingexercisegyg.di

import com.rs.codingexercisegyg.reviewslist.ReviewsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindReviewsListFragment(): ReviewsListFragment
}