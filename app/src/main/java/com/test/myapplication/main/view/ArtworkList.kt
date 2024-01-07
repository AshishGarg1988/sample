package com.test.myapplication.main.view
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.test.myapplication.main.model.Artwork

@Composable
fun ArtworkList(artworks: List<Artwork>) {
    LazyColumn {
        items(artworks) { artwork ->
            ArtworkItem(artwork)
        }
    }
}