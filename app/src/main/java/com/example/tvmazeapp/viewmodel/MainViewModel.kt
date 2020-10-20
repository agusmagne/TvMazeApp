package com.example.tvmazeapp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.collection.LruCache
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvmazeapp.cache.TvShowBitmapCache
import com.example.tvmazeapp.model.TvShow
import com.example.tvmazeapp.services.TvMazeEndpoints
import com.example.tvmazeapp.services.TvMazeService
import com.example.tvmazeapp.utils.Utils
import com.example.tvmazeapp.view.MainView
import org.joda.time.DateTime
import org.joda.time.Days
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val view: MainView) : ViewModel() {

    val liveData = MutableLiveData<Pair<TvShow, Bitmap?>>()

    private val maxCacheSize = Utils.getCacheMaxSize(view as Context)
    private val tvShowBitmapCache = TvShowBitmapCache(maxCacheSize)
    private val tvShowCache = LruCache<String, TvShow>(maxCacheSize / 100)
    private var inputString = ""

    fun getTvShow(inputString: String) {
        view.showProgress()
        this.inputString = inputString
        if (tvShowCache[inputString] != null) {
            // do not call the api at all
            val tvShow = tvShowCache[inputString]!!
            val bitmap = tvShowBitmapCache[tvShow.id]
            liveData.value = Pair(tvShow, bitmap)

        } else {
            val service = TvMazeService.getService().create(TvMazeEndpoints::class.java)
            service.getTvShow(inputString).enqueue(getTvShowCallback)
        }
    }

    fun storeInCache(tvShow: TvShow, resource: Drawable?) {
        resource?.let {
            tvShowBitmapCache.put(tvShow.id, it.toBitmap())
            tvShowCache.put(inputString, tvShow)
        }
    }

    private val getTvShowCallback = object : Callback<TvShow> {
        override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
            response.body()?.let {
                view.hideProgress()
                val tvShowRaw = it
                tvShowRaw.premiered = calculatePremieredDays(tvShowRaw.premiered)
                val pair = if (tvShowBitmapCache[tvShowRaw.id] == null) {
                    Pair(tvShowRaw, null)
                } else {
                    Pair(tvShowRaw, tvShowBitmapCache[tvShowRaw.id])
                }
                liveData.value = pair
            }
        }

        override fun onFailure(call: Call<TvShow>, t: Throwable) {
            view.hideProgress()
            view.makeToast("Error trying to get repositories")
            println(t.message)
            println(t.localizedMessage)
        }
    }

    private fun calculatePremieredDays(dateRaw: String?): String =
        "Updated ${
            Days.daysBetween(
                DateTime.parse(dateRaw),
                DateTime.now()
            ).days
        } days ago"


}