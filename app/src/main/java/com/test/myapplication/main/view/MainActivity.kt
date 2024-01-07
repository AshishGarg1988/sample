package com.test.myapplication.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.test.myapplication.main.viewmodel.ArtworkViewModel
import com.test.myapplication.ui.theme.MyApplicationTheme
import com.test.myapplication.utility.Utils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkUtils = Utils(this)
        val viewModel: ArtworkViewModel by lazy {
            ViewModelProvider(this).get(ArtworkViewModel::class.java)
        }
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(networkUtils.isNetworkAvailable()){
                        if (viewModel.loading.value) {
                            // Display a ProgressBar while loading
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
                            // Display the ArtworkList once data is fetched
                            ArtworkList(viewModel.artworks.value)
                        }
                    }else{
                        Text(
                            text = "No Network available",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                }
            }
        }
    }
}
