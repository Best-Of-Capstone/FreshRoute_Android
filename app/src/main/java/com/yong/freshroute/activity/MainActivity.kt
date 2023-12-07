package com.yong.freshroute.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yong.freshroute.R
import com.yong.freshroute.util.AuthUtil
import com.yong.freshroute.util.PermissionUtil.checkLocationPermission
import com.yong.freshroute.util.PermissionUtil.openAppInfo

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mainAnimWeather: LottieAnimationView
    private lateinit var mainBtnSearch: LinearLayout
    private lateinit var mainGreetText: TextView
    private lateinit var mainWeatherDescText: TextView
    private lateinit var mainWeatherInfoText: TextView
    private lateinit var mainWeatherTempText: TextView
    private lateinit var mainWelcomeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBtnSearch = findViewById(R.id.btn_main_search)
        mainBtnSearch.setOnClickListener(btnListener)

        mainAnimWeather = findViewById(R.id.anim_main_weather)
        mainGreetText = findViewById(R.id.tv_main_greeting)
        mainWeatherDescText = findViewById(R.id.tv_main_weather_desc)
        mainWeatherInfoText = findViewById(R.id.tv_main_weather_info)
        mainWeatherTempText = findViewById(R.id.tv_main_weather_temp)
        mainWelcomeText = findViewById(R.id.tv_main_welcome)

        mainWeatherInfoText.text = getString(R.string.main_tv_weather_default)
        mainGreetText.text = String.format(getString(R.string.main_tv_welcome_format), AuthUtil.getUserInfo()!!.Name)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    override fun onResume() {
        super.onResume()

        if(!checkLocationPermission(applicationContext)) {
            openAppInfo(applicationContext)
        } else {
            // Update Weather
            updateWeatherView("Today will be sunny", 25, "SUNNY")
        }
    }

    private fun updateWeatherView(desc: String, temp: Int, type: String) {
        mainAnimWeather.setAnimation(R.raw.anim_weather_sunny)
        mainWeatherDescText.text = desc
        mainWeatherInfoText.text = type
        mainWeatherTempText.text = temp.toString()
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_main_search -> startActivity(Intent(applicationContext, SearchActivity::class.java))
        }
    }
}