package com.yong.freshroute.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.preference.PreferenceManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.button.MaterialButton
import com.yong.freshroute.R
import com.yong.freshroute.util.AuthUtil

class LoginActivity : AppCompatActivity() {
    private lateinit var btnGoogleLogin: MaterialButton
    private lateinit var pref: SharedPreferences
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnGoogleLogin = findViewById(R.id.btn_login_google)
        btnGoogleLogin.setOnClickListener(btnListener)

        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if(pref.getBoolean("isFirst", true)) {
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
        }

        signInLauncher = this.registerForActivityResult(
            FirebaseAuthUIActivityResultContract(),
        ) { res ->
            this.onSignInResult(res)
        }
    }

    private fun onSignInResult(res: FirebaseAuthUIAuthenticationResult) {
        if(res.resultCode == RESULT_OK) {
            val curUser = AuthUtil.getUserInfo()
            if(curUser == null) {
                Toast.makeText(applicationContext, "Failed Login", Toast.LENGTH_LONG).show()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } else {
            Toast.makeText(applicationContext, "Failed Login", Toast.LENGTH_LONG).show()
        }
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_login_google -> {
                val signInProvider = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(signInProvider)
                    .build()
                signInLauncher.launch(signInIntent)
            }
        }
    }
}