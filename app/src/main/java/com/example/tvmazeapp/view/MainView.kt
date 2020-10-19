package com.example.tvmazeapp.view

import com.example.tvmazeapp.model.TvShow

interface MainView {

    fun showProgress()
    fun hideProgress()
    fun makeToast(message: String)

}