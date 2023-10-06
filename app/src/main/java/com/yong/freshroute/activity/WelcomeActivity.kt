package com.yong.freshroute.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.yong.freshroute.R

class WelcomeActivity : AppCompatActivity() {
    private lateinit var btnLocatonPermission: AppCompatButton
    private lateinit var btnNext: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnLocatonPermission = findViewById(R.id.btn_welcome_permission_location)
        btnLocatonPermission.setOnClickListener(btnListener)
        btnNext = findViewById(R.id.btn_welcome_next)
        btnNext.setOnClickListener(btnListener)
        btnNext.isEnabled = false
    }

    private val btnListener = View.OnClickListener { item ->
        when(item.id) {
            R.id.btn_welcome_permission_location -> {
                requestLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
            }
            R.id.btn_welcome_next -> {
                val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val prefEditor = pref.edit()

                prefEditor.putBoolean("isFirst", false)
                prefEditor.apply()
                finish()
            }
        }
    }

    private val requestLocationPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(applicationContext, applicationContext.getString(R.string.noti_toast_no_permission), Toast.LENGTH_LONG).show()
            val permissionIntent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                addCategory(Intent.CATEGORY_DEFAULT)
                data = Uri.parse("package:com.yong.freshroute")
            }
            applicationContext.startActivity(permissionIntent)
        } else {
            btnLocatonPermission.isEnabled = false
            btnNext.isEnabled = true
        }
    }
}