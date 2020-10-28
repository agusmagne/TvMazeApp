package com.example.tvmazeapp.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tvmazeapp.R
import com.example.tvmazeapp.model.TvShow
import com.example.tvmazeapp.viewmodel.MainViewModel
import com.example.tvmazeapp.viewmodel.MainViewModel2
import com.example.tvmazeapp.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    private val TAG = MainActivity::class.qualifiedName

    @Inject
    lateinit var providersFactory: ViewModelProvidersFactory
    private lateinit var viewModel2: MainViewModel2

    @Inject
    @JvmField
    var logo: Drawable? = null

    @Inject
    lateinit var glideRequestManager: RequestManager

    private var viewModel: MainViewModel? = null
    private var progressbar: ProgressBar? = null
    private var edtxt: EditText? = null
    private var titleTxt: TextView? = null
    private var logoImageView: ImageView? = null
    private var tvShowImageView: ImageView? = null
    private var premieredTxt: TextView? = null
    private var noResultsTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setSearchListener()
        loadIconDrawable()

        viewModel = MainViewModel(this)
        viewModel?.liveData?.observe(this) { tvShowPair -> bindTvShow(tvShowPair) }
        viewModel2 = ViewModelProvider(this, providersFactory).get(MainViewModel2::class.java)
        Log.d(TAG, "onCreate: ${viewModel2.string}")

    }

    override fun showProgress() {
        progressbar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar?.visibility = View.INVISIBLE
    }

    override fun makeToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun handleShowVisibility(isTvShowVisible: Boolean) {
        if (isTvShowVisible) {
            tvShowImageView?.visibility = View.VISIBLE
            titleTxt?.visibility = View.VISIBLE
            premieredTxt?.visibility = View.VISIBLE
            noResultsTxt?.visibility = View.INVISIBLE
            animate(tvShowImageView)
        } else {
            tvShowImageView?.visibility = View.INVISIBLE
            titleTxt?.visibility = View.INVISIBLE
            premieredTxt?.visibility = View.INVISIBLE
            noResultsTxt?.visibility = View.VISIBLE
            animate(noResultsTxt)
        }
    }

    private fun loadIconDrawable() = logoImageView?.let {
        glideRequestManager.load(logo).into(it)
    }

    private fun bindTvShow(tvShowPair: Pair<TvShow, Bitmap?>) {
        titleTxt?.text = tvShowPair.first.name
        premieredTxt?.text = tvShowPair.first.premiered
        tvShowImageView?.let {
            if (tvShowPair.second == null) {
                tvShowPair.first.image?.original?.let { uri ->
                    glideRequestManager
                        .load(uri)
                        .listener(imageviewCallback(tvShowPair.first))
                        .into(it)
                }
            } else {
                tvShowPair.second?.let { bitmap ->
                    glideRequestManager.load(bitmap).into(it)
                }
                hideProgress()
                handleShowVisibility(true)
            }
        }
    }

    private fun initViews() {
        edtxt = findViewById(R.id.title_edtxt)
        titleTxt = findViewById(R.id.title_txt)
        premieredTxt = findViewById(R.id.premiered_txt)
        progressbar = findViewById(R.id.progressbar)
        logoImageView = findViewById(R.id.icon)
        tvShowImageView = findViewById(R.id.imageview)
        noResultsTxt = findViewById(R.id.no_results_found_txt)
    }

    private fun animate(view: View?) {
        val anim1 = AnimatorInflater.loadAnimator(this, R.animator.resize_alpha)
        val anim2 = AnimatorInflater.loadAnimator(this, R.animator.regain_alpha)
        val set = AnimatorSet()
        set.playTogether(anim1, anim2)
        set.setTarget(view)
        set.start()
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
            handleShowVisibility(true)
            return false
        }
    }


}