package com.example.tvmazeapp.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.tvmazeapp.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    @JvmField
    var logo: Drawable? = null

    @Inject
    lateinit var glideRequestManager: RequestManager

    private var loginBtn: Button? = null
    private var logoImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        bindViews()
        setClickListeners()
        loadLogo()
    }

    private fun bindViews() {
        loginBtn = findViewById(R.id.login_btn)
        logoImageView = findViewById(R.id.icon)
    }

    private fun setClickListeners() {
        loginBtn?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("USERNAME", loginBtn?.text?.toString()?.trim())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadLogo() {
        logoImageView?.let { glideRequestManager.load(logo).into(it) }
    }
}