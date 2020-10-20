package com.example.tvmazeapp.view

interface MainView {

    fun showProgress()
    fun hideProgress()
    fun makeToast(message: String)
    fun handleShowVisibility(isTvShowVisible: Boolean)
}