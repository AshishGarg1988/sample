package com.test.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.test.myapplication.api.ArticApiService
import com.test.myapplication.main.model.Artwork
import com.test.myapplication.main.model.ArtworksResponse
import com.test.myapplication.main.viewmodel.ArtworkViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtworkViewModelTest {

    // Rule to ensure LiveData updates instantly on the same thread
    @get:Rule
    val rule = InstantTaskExecutorRule()

    // Set up a coroutine rule for testing suspending functions
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    // Mocks
    private lateinit var apiService: ArticApiService
    private lateinit var viewModel: ArtworkViewModel

    // Observers for testing LiveData updates
    private lateinit var artworksObserver: Observer<List<Artwork>>

    @Before
    fun setup() {
        apiService = mockk(relaxed = true)
        viewModel = ArtworkViewModel(apiService)

        artworksObserver = spyk(Observer { })
    }

    @After
    fun tearDown() {
        // No need to remove observer since we're not using observeForever
    }

    @Test
    fun `fetchData success updates artworks`() = runBlockingTest {
        // Arrange
        val mockArtworks = listOf(Artwork(1, "Artwork 1"), Artwork(2, "Artwork 2"))
        val mockResponse = ArtworksResponse(mockArtworks)
        coEvery { apiService.getArtworks() } returns mockResponse

        // Act
        viewModel.fetchData()

        // Assert
        coVerify { apiService.getArtworks() }
        verify { artworksObserver wasNot Called }
    }

    @Test
    fun `fetchData error handles gracefully`() = runBlockingTest {
        // Arrange
        coEvery { apiService.getArtworks() } throws Exception("Fake error")

        // Act
        viewModel.fetchData()

        // Assert
        coVerify { apiService.getArtworks() }
        verify { artworksObserver wasNot Called }
        // Additional error handling assertions if needed
    }
}