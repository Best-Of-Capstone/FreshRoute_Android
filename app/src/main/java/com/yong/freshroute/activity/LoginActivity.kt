package com.yong.freshroute.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import com.yong.freshroute.R
import com.yong.freshroute.util.AuthUtil

class LoginActivity : AppCompatActivity() {
    private lateinit var btnGoogleLogin: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnGoogleLogin = findViewById(R.id.btn_login_google)
        btnGoogleLogin.setOnClickListener(btnListener)
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_login_google -> AuthUtil.tryLogin()
        }
    }
}