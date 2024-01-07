package com.test.myapplication.api

import com.test.myapplication.detail.model.ArtworkDetail
import com.test.myapplication.main.model.ArtworksResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticApiService {
    @GET("artworks?page=2&limit=10")
    suspend fun getArtworks(): ArtworksResponse

    @GET("artworks/{artworkId}")
    suspend fun getArtworkDetails(@Path("artworkId") artworkId: Int): ArtworkDetail
}