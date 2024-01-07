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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.test.myapplication.detail.viewmodel.ArtworkDetailViewModel
import com.test.myapplication.utility.Utils

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the artworkId from the intent
        val networkUtils = Utils(this)
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
                ArtworkDetails(viewModel, networkUtils)
            }

        }
        viewModel.fetchArtworkDetails(artworkId)
    }

}

@Composable
fun ArtworkDetails(artworkviemodel: ArtworkDetailViewModel, networkUtils: Utils) {

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
            if(networkUtils.isNetworkAvailable()){
                Text(
                    text = artworkviemodel.artworkTitle.value ?: "No title available",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = htmlToPlainText(artworkviemodel.artist_display.value),
                    modifier = Modifier
                        .padding(6.dp)
                )
                ScrollableText(
                    text = htmlToPlainText(artworkviemodel.artworkdesc.value ?: "No Description Available Now")
                )
            }else{
                Text(
                    text = "No Network available",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

        }

    }
}

@Composable
fun ScrollableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier
) {
    // You can adjust the verticalScroll modifier as needed
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Color.LightGray)
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
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
