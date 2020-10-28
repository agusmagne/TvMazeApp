package com.example.tvmazeapp.model

import android.graphics.Bitmap

class TvShow(
    val id: String,
    val name: String = "",
    val image: Image? = Image(""),
    var premiered: String = "",
    cachedBitmap: Bitmap? = null
)

class Image(val original: String)