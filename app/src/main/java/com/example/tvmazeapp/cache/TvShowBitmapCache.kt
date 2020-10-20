package com.example.tvmazeapp.cache

import android.graphics.Bitmap
import androidx.collection.LruCache

class TvShowBitmapCache(maxSize: Int): LruCache<String, Bitmap>(maxSize) {

    override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount

}