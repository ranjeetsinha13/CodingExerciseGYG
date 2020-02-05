package com.rs.codingexercisegyg.di

import com.rs.codingexercisegyg.ui.HomeActivity
import com.rs.codingexercisegyg.ui.ReviewDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindReviewDetailsActivity(): ReviewDetailsActivity
}