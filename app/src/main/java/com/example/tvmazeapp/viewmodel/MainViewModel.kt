package com.example.tvmazeapp.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.collection.LruCache
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvmazeapp.R
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
import javax.inject.Inject

class MainViewModel(private val view: MainView) : ViewModel() {

    val liveData = MutableLiveData<Pair<TvShow, Bitmap?>>()

//    private var maxCacheSize: Int
//    private val tvShowBitmapCache: TvShowBitmapCache
//    private val tvShowCache: LruCache<String, TvShow>
    private var inputString: String

    init {
//        maxCacheSize = Utils.getCacheMaxSize(view as Context)
//        tvShowBitmapCache = TvShowBitmapCache(maxCacheSize)
//        tvShowCache = LruCache(maxCacheSize / 100)
        inputString = ""
    }

//    fun getTvShow(inputString: String) {
//        if (inputString.isNotBlank()) {
//            view.showProgress()
//            this.inputString = inputString
//            if (tvShowCache[inputString] != null) {
//                // do not call the api at all
//                val tvShow = tvShowCache[inputString]!!
//                val bitmap = tvShowBitmapCache[tvShow.id]
//                liveData.value = Pair(tvShow, bitmap)
//
//            } else {
//                val service = TvMazeService.getService().create(TvMazeEndpoints::class.java)
//                val call = service.getTvShow(inputString)
//                call.enqueue(getTvShowCallback)
//            }
//        } else {
//            view.makeToast((view as Context).getString(R.string.ERROR_EMPTY_EDTXT))
//        }
//    }

    fun storeInCache(tvShow: TvShow, resource: Drawable?) {
        resource?.let {
//            tvShowBitmapCache.put(tvShow.id, it.toBitmap())
//            tvShowCache.put(inputString, tvShow)
        }
    }

//    private val getTvShowCallback = object : Callback<TvShow> {
//        override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
//            val tvShowRaw = response.body()
//            view.hideProgress()
//            if (tvShowRaw != null) {
//                tvShowRaw.premiered = calculatePremieredDays(tvShowRaw.premiered)
//                val pair = if (tvShowBitmapCache[tvShowRaw.id] == null) {
//                    Pair(tvShowRaw, null)
//                } else {
//                    Pair(tvShowRaw, tvShowBitmapCache[tvShowRaw.id])
//                }
//                liveData.value = pair
//            } else {
//                view.handleShowVisibility(false)
//            }
//        }
//
//        override fun onFailure(call: Call<TvShow>, t: Throwable) {
//            view.hideProgress()
//            view.makeToast((view as Context).getString(R.string.SERVICE_ERROR))
//            println(t.message)
//            println(t.localizedMessage)
//        }
//    }

    private fun calculatePremieredDays(dateRaw: String?): String =
        "Updated ${
            Days.daysBetween(
                DateTime.parse(dateRaw),
                DateTime.now()
            ).days
        } days ago"


}