package com.test.myapplication

import com.test.myapplication.detail.model.ArtworkDetail
import com.test.myapplication.detail.model.Data
import com.test.myapplication.detail.viewmodel.ArtworkDetailViewModel
import com.test.myapplication.repository.ArtworkRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ArtworkDetailViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `fetchArtworkDetails success`() = testScope.runBlockingTest {
        // Given
        val mockRepository = mockk<ArtworkRepository>()
        val expectedArtworkDetail = ArtworkDetail(
            id = 198539,
            data = Data(
                id = 198539,
                title = "Mocked Title",
                description = "Mocked Description",
                artist_display = "Mocked Artist"
            )
        )
        coEvery { mockRepository.getArtworkDetails(198539) } returns expectedArtworkDetail

        val viewModel = ArtworkDetailViewModel()
        viewModel.repository = mockRepository

        // When
        viewModel.fetchArtworkDetails(198539)
        Thread.sleep(5000)
        // Then
        assertEquals("Mocked Title", viewModel.artworkTitle.value)
        assertEquals("Mocked Description", viewModel.artworkdesc.value)
        assertEquals("Mocked Artist", viewModel.artist_display.value)
    }

    @Test
    fun `fetchArtworkDetails failure`() = testScope.runBlockingTest {
        // Given
        val mockRepository = mockk<ArtworkRepository>()
        coEvery { mockRepository.getArtworkDetails(198539) } throws Exception("Mocked error")

        val viewModel = ArtworkDetailViewModel()
        viewModel.repository = mockRepository

        // When
        viewModel.fetchArtworkDetails(198539)

        // Then
        assertTrue(viewModel.artworkTitle.value.isEmpty())
        assertTrue(viewModel.artworkdesc.value.isEmpty())
        assertTrue(viewModel.artist_display.value.isEmpty())
    }
}