package com.test.myapplication.repository

import com.test.myapplication.detail.model.ArtworkDetail
import com.test.myapplication.api.ArticApiService
import com.test.myapplication.main.model.ArtworksResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtworkRepository {
    private val api: ArticApiService = Retrofit.Builder()
        .baseUrl("https://api.artic.edu/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ArticApiService::class.java)

    suspend fun getArtworkDetails(artworkId: Int): ArtworkDetail {
        return api.getArtworkDetails(artworkId)
    }

    suspend fun getArtworks(): ArtworksResponse {
        return api.getArtworks()
    }
}