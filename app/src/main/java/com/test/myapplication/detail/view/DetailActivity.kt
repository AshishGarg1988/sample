package com.test.myapplication.detail.view

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.test.myapplication.detail.viewmodel.ArtworkDetailViewModel

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the artworkId from the intent
        val artworkId = intent.getIntExtra("artworkId", -1)
        val viewModel: ArtworkDetailViewModel by lazy {
            ViewModelProvider(this).get(ArtworkDetailViewModel::class.java)
        }

        // Set up Compose content
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ){
                ArtworkDetails(viewModel)
            }

        }
        viewModel.fetchArtworkDetails(artworkId)
    }

}

@Composable
fun ArtworkDetails(artworkviemodel: ArtworkDetailViewModel) {

    if (artworkviemodel.loading.value) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = artworkviemodel.artworkTitle.value ?: "No title available",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = htmlToPlainText(artworkviemodel.artworkdesc.value),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.LightGray)
                    .padding(8.dp)
            )
        }

    }
}

@Composable
fun htmlToPlainText(htmlString: String): AnnotatedString {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        AnnotatedString(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString())
    } else {
        @Suppress("DEPRECATION")
        AnnotatedString(Html.fromHtml(htmlString).toString())
    }
}
