package com.example.tvmazeapp.utils

import android.app.ActivityManager
import android.content.Context

object Utils {

    fun getCacheMaxSize(context: Context): Int =
        ((context.getSystemService(Context.ACTIVITY_SERVICE)
                as ActivityManager).memoryClass * 1024 * 1024) / 8

}