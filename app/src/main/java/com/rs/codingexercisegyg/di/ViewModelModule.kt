package com.rs.codingexercisegyg.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rs.codingexercisegyg.reviewslist.ReviewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReviewsListViewModel::class)
    abstract fun bindReviewsListViewModel(homeViewModel: ReviewsListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GYGViewModelFactory): ViewModelProvider.Factory
}
