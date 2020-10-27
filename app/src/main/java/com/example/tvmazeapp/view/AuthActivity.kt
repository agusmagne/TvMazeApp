package com.example.tvmazeapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tvmazeapp.R

class AuthActivity : AppCompatActivity() {

    private var loginBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        bindViews()
        setClickListeners()
    }

    private fun bindViews() {
        loginBtn = findViewById(R.id.login_btn)
    }

    private fun setClickListeners() {
        loginBtn?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("USERNAME", loginBtn?.text?.toString()?.trim())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}