package com.test.myapplication.detail.viewmodel

import android.app.Application
import android.text.Html
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.repository.ArtworkRepository
import com.test.myapplication.utility.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtworkDetailViewModel() : ViewModel() {

    var repository = ArtworkRepository() // Create a repository class

    // LiveData to observe the artwork title
    val artworkTitle: MutableState<String> = mutableStateOf("")
    val artworkID: MutableState<String> = mutableStateOf("")
    val artworkdesc: MutableState<String> = mutableStateOf("")
    val artworkDetails: MutableState<String> = mutableStateOf("")
    val artist_display: MutableState<String> = mutableStateOf("")
    private val _loading = mutableStateOf(true)
    val loading: State<Boolean> = _loading

    // Function to fetch artwork details
    fun fetchArtworkDetails(artworkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val artwork = repository.getArtworkDetails(artworkId)
                artworkDetails.value = artwork.toString()
                artworkTitle.value = artwork.data.title
                artworkdesc.value = artwork.data.description
                artist_display.value = artwork.data.artist_display
                artworkID.value = artwork.data.id.toString()

            }catch (e : Exception){

            }finally {
                _loading.value = false
            }

        }
    }


}