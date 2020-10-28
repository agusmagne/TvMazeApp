package com.example.tvmazeapp.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvmazeapp.model.TvShow
import com.example.tvmazeapp.services.TvMazeEndpoints
import com.example.tvmazeapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel2 @Inject constructor(): ViewModel() {

    private val TAG = MainViewModel2::class.qualifiedName

    val liveData = MutableLiveData<Pair<TvShow, Bitmap?>>()

    @Inject
    lateinit var tvMazeEndpoints: TvMazeEndpoints

    @Inject
    lateinit var utils: Utils

    val string = "String"

    fun getTvShowData(inputString: String) {
        val maxCacheSize = utils.getCacheMaxSize()
        Log.d(TAG, "getTvShowData: $maxCacheSize")
        if (inputString.isNotBlank()) {
            tvMazeEndpoints.getTvShow(inputString).enqueue(callback)
        } else {
            Log.d(TAG, "getTvShowData: ")
        }
    }

    private val callback = object: Callback<TvShow> {
        override fun onFailure(call: Call<TvShow>, t: Throwable) {
            Log.d(TAG, "onFailure: Data fetch failed!")
        }

        override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
            val pair: Pair<TvShow, Bitmap?> = Pair(response.body()!!, null)
            liveData.value = pair
            Log.d(TAG, "onResponse: Data fetch successful!")
        }

    }

}