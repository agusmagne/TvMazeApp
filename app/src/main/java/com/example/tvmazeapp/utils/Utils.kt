package com.example.tvmazeapp.utils

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import javax.inject.Inject

class Utils @Inject constructor(private val application: Application) {

    fun getCacheMaxSize(): Int =
        ((application.getSystemService(Context.ACTIVITY_SERVICE)
                as ActivityManager).memoryClass * 1024 * 1024) / 8

}