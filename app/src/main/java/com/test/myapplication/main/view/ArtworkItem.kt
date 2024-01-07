package com.test.myapplication.main.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.test.myapplication.detail.view.DetailActivity
import com.test.myapplication.main.model.Artwork

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkItem(artwork: Artwork) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("artworkId", artwork.id) // Pass any necessary data to the detail activity
            context.startActivity(intent)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = artwork.title, style = MaterialTheme.typography.headlineSmall)
            // Add more details if needed
        }
    }
}