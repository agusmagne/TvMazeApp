package com.example.tvmazeapp.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tvmazeapp.R
import com.example.tvmazeapp.model.TvShow
import com.example.tvmazeapp.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity(), MainView {

    private var viewModel: MainViewModel? = null
    private var progress: ProgressBar? = null
    private var edtxt: EditText? = null
    private var titleTxt: TextView? = null
    private var image: ImageView? = null
    private var premieredTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setClickListeners()
        viewModel = MainViewModel(this)
        viewModel?.liveData?.observe(this, tvShowObserver)
    }

    override fun showProgress() {
        progress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress?.visibility = View.INVISIBLE
    }

    override fun makeToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun bindTvShow(tvShow: TvShow) {
        titleTxt?.text = tvShow.name
        premieredTxt?.text = tvShow.premiered
        Picasso.with(this).load(tvShow.image.original).into(image)
    }

    private fun initViews() {

    }

    private fun setClickListeners() {
        edtxt?.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel?.getTvShow(textView.toString().trim())
            false
        }
    }

    private val tvShowObserver = Observer<TvShow?> {
        bindTvShow(it)
    }
}