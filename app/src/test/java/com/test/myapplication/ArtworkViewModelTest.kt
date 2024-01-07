package com.test.myapplication

import com.test.myapplication.main.model.Artwork
import com.test.myapplication.main.model.ArtworksResponse
import com.test.myapplication.main.viewmodel.ArtworkViewModel
import com.test.myapplication.repository.ArtworkRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class ArtworkViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchData should update artworks and loading states`() {
        // Given
        val mockRepository = mockk<ArtworkRepository>()

        // Mocking the behavior of the repository
        coEvery { mockRepository.getArtworks() } returns ArtworksResponse(listOf(Artwork(1, "Title")))

        val viewModel = ArtworkViewModel()

        // Mocking the repository inside the ViewModel
        viewModel.repository = mockRepository

        // When
        viewModel.fetchData()

        // Then
        assertEquals(viewModel.artworks.value.size, 1)
        assertEquals(viewModel.loading.value, false)
    }
}