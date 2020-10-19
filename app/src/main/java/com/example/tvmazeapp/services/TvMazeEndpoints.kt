package com.example.tvmazeapp.services

import com.example.tvmazeapp.model.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeEndpoints {

    @GET(TvMazeService.SINGLE_SEARCH_PATH)
    fun getTvShow(@Query("q") name: String) : Call<TvShow>

}