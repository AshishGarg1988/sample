package com.test.myapplication.main.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.test.myapplication.repository.ArtworkRepository
import com.test.myapplication.main.model.Artwork
import kotlinx.coroutines.Dispatchers

class ArtworkViewModel() : ViewModel() {
    private val _artworks = mutableStateOf<List<Artwork>>(emptyList())
    val artworks: State<List<Artwork>> = _artworks
    private val _loading = mutableStateOf(true)
    val loading: State<Boolean> = _loading
    private val repository = ArtworkRepository()
    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getArtworks()
                _artworks.value = response.data
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }

    }
}