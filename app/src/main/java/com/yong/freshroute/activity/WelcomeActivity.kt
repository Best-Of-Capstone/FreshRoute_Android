package com.yong.freshroute.activity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.preference.PreferenceManager
import com.yong.freshroute.R
import com.yong.freshroute.util.PermissionUtil
import com.yong.freshroute.util.PermissionUtil.checkLocationPermission

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
                if(!checkLocationPermission(applicationContext)) {
                    requestLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
                }
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
        if(checkLocationPermission(applicationContext)) {
            btnLocatonPermission.isEnabled = false
            btnNext.isEnabled = true
        } else {
            PermissionUtil.openAppInfo(applicationContext)
        }
    }
}