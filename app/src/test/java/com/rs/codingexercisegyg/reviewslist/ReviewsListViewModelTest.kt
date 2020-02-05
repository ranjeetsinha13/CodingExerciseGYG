package com.rs.codingexercisegyg.reviewslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.tvmaze.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ReviewsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var reviewsDataSourceFactory: ReviewsDataSourceFactory

    @Mock
    private lateinit var reviewDataSource: ReviewsDataSource

    @InjectMocks
    private lateinit var reviewsListViewModel: ReviewsListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetReviews() {
        reviewsListViewModel.onScreenCreated()
        Assert.assertTrue(reviewsListViewModel.getReviews().value == null)
    }

    @Test
    @Ignore("Not working .. FIXME")
    fun testInitialLoadState() {
        reviewsDataSourceFactory.create()
        reviewsListViewModel.initialLoadState()
    }
}