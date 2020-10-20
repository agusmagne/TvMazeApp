package com.example.tvmazeapp.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tvmazeapp.R
import com.example.tvmazeapp.model.TvShow
import com.example.tvmazeapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainView {

    private var viewModel: MainViewModel? = null
    private var progressbar: ProgressBar? = null
    private var edtxt: EditText? = null
    private var titleTxt: TextView? = null
    private var imageview: ImageView? = null
    private var premieredTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setSearchListener()
        viewModel = MainViewModel(this)
        viewModel?.liveData?.observe(this, { tvShowPair -> bindTvShow(tvShowPair) })
    }

    override fun showProgress() {
        progressbar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar?.visibility = View.INVISIBLE
    }

    override fun makeToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun bindTvShow(tvShowPair: Pair<TvShow, Bitmap?>) {
        titleTxt?.text = tvShowPair.first.name
        premieredTxt?.text = tvShowPair.first.premiered
        imageview?.let {
            if (tvShowPair.second == null) {
                Glide.with(this)
                    .load(tvShowPair.first.image.original)
                    .listener(imageviewCallback(tvShowPair.first))
                    .into(it)
            } else {
                Glide.with(this).load(tvShowPair.second).into(it)
                hideProgress()
            }
        }
    }

    private fun initViews() {
        edtxt = findViewById(R.id.title_edtxt)
        titleTxt = findViewById(R.id.title_txt)
        premieredTxt = findViewById(R.id.premiered_txt)
        progressbar = findViewById(R.id.progressbar)
        imageview = findViewById(R.id.imageview)
    }

    private fun setSearchListener() {
        edtxt?.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel?.getTvShow(textView.text.toString().trim())
            false
        }
    }

    private fun imageviewCallback(tvShow: TvShow) = object : RequestListener<Drawable?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable?>?,
            isFirstResource: Boolean
        ): Boolean {
            makeToast("Error loading bitmap of ${tvShow.name}")
            return true
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            viewModel?.storeInCache(tvShow, resource)
            return false
        }
    }


}