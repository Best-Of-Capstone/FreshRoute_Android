package com.yong.freshroute.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.yong.freshroute.R
import com.yong.freshroute.util.AuthUtil

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if(AuthUtil.isLoggedIn() && AuthUtil.isTokenAvail()){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }, 2000)
    }
}