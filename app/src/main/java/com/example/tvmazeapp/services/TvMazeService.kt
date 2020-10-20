package com.example.tvmazeapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvMazeService {

    private const val BASE_URL = "https://api.tvmaze.com"
    const val SINGLE_SEARCH_PATH = "/singlesearch/shows"

    fun getService(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

}