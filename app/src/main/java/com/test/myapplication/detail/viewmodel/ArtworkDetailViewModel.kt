package com.test.myapplication.detail.viewmodel

import android.text.Html
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.repository.ArtworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtworkDetailViewModel() : ViewModel() {

    private val repository = ArtworkRepository() // Create a repository class

    // LiveData to observe the artwork title
    val artworkTitle: MutableState<String> = mutableStateOf("")
    val artworkdesc: MutableState<String> = mutableStateOf("")
    private val _loading = mutableStateOf(true)
    val loading: State<Boolean> = _loading
    // Function to fetch artwork details
    fun fetchArtworkDetails(artworkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val artwork = repository.getArtworkDetails(artworkId)
                artworkTitle.value = artwork.data.title
                artworkdesc.value = artwork.data.description
            }catch (e : Exception){

            }finally {
                _loading.value = false
            }

        }
    }


}