package com.example.tvmazeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvmazeapp.model.TvShow
import com.example.tvmazeapp.services.TvMazeEndpoints
import com.example.tvmazeapp.services.TvMazeService
import com.example.tvmazeapp.view.MainView
import org.joda.time.DateTime
import org.joda.time.Days
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(view: MainView) : ViewModel() {

    val liveData = MutableLiveData<TvShow>()

    fun getTvShow(name: String) {
        val service = TvMazeService.getService().create(TvMazeEndpoints::class.java)
        service.getTvShow(name).enqueue(getTvShowCallback)
    }

    private val getTvShowCallback = object : Callback<TvShow> {
        override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
            val tvShowRaw = response.body()
            tvShowRaw?.premiered = calculatePremieredDays(tvShowRaw?.premiered)
            liveData.value = tvShowRaw
        }

        override fun onFailure(call: Call<TvShow>, t: Throwable) =
            view.makeToast("Error trying to get repositories")


    }

    private fun calculatePremieredDays(dateRaw: String?): String =
        Days.daysBetween(DateTime.parse(dateRaw), DateTime.now()).days.toString()

}