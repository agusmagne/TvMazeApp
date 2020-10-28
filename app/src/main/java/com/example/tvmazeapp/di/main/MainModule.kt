package com.example.tvmazeapp.di.main

import com.example.tvmazeapp.services.TvMazeEndpoints
import com.example.tvmazeapp.services.TvMazeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MainModule {

    @Provides
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(TvMazeService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    fun provideTvMazeApi(retrofit: Retrofit): TvMazeEndpoints =
        retrofit.create(TvMazeEndpoints::class.java)

}