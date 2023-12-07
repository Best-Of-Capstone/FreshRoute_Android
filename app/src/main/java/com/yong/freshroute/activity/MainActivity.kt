package com.yong.freshroute.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yong.freshroute.R
import com.yong.freshroute.util.AuthUtil
import com.yong.freshroute.util.PermissionUtil.checkLocationPermission
import com.yong.freshroute.util.PermissionUtil.openAppInfo
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mainAnimWeather: LottieAnimationView
    private lateinit var mainBtnSearch: LinearLayout
    private lateinit var mainGreetCard: CardView
    private lateinit var mainGreetText: TextView
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var mainWeatherCard: CardView
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
        mainGreetCard = findViewById(R.id.card_main_greeting)
        mainGreetText = findViewById(R.id.tv_main_greeting)
        mainLayout = findViewById(R.id.layout_main)
        mainWeatherCard = findViewById(R.id.card_main_weather)
        mainWeatherDescText = findViewById(R.id.tv_main_weather_desc)
        mainWeatherInfoText = findViewById(R.id.tv_main_weather_info)
        mainWeatherTempText = findViewById(R.id.tv_main_weather_temp)
        mainWelcomeText = findViewById(R.id.tv_main_welcome)

        mainWeatherInfoText.text = getString(R.string.main_tv_weather_default)
        mainGreetText.text = String.format(getString(R.string.main_tv_welcome_format), AuthUtil.getUserInfo()!!.Name)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        updateColorTheme()
    }

    override fun onResume() {
        super.onResume()

        if(!checkLocationPermission(applicationContext)) {
            openAppInfo(applicationContext)
        } else {
            updateWeatherView("Today will be sunny", 25, "SUNNY")
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(): Int {
        return SimpleDateFormat("HH").format(System.currentTimeMillis()).toInt()
    }

    private fun updateColorTheme() {
        if(getTime() < 18 || getTime() >= 6){
            mainGreetCard.setCardBackgroundColor(getColor(R.color.card_bg_day))
            mainWeatherCard.setCardBackgroundColor(getColor(R.color.card_bg_day))
            mainLayout.background = ColorDrawable(getColor(R.color.weather_bg_day))
            mainGreetText.setTextColor(getColor(R.color.card_text_day))
            mainWeatherDescText.setTextColor(getColor(R.color.card_text_day))
            mainWeatherInfoText.setTextColor(getColor(R.color.weather_text_day))
            mainWeatherTempText.setTextColor(getColor(R.color.weather_text_day))
            mainWelcomeText.setTextColor(getColor(R.color.card_text_day))
        }else{
            mainGreetCard.setCardBackgroundColor(getColor(R.color.card_bg_night))
            mainWeatherCard.setCardBackgroundColor(getColor(R.color.card_bg_night))
            mainLayout.background = ColorDrawable(getColor(R.color.weather_bg_night))
            mainGreetText.setTextColor(getColor(R.color.card_text_night))
            mainWeatherDescText.setTextColor(getColor(R.color.card_text_night))
            mainWeatherInfoText.setTextColor(getColor(R.color.weather_text_night))
            mainWeatherTempText.setTextColor(getColor(R.color.weather_text_night))
            mainWelcomeText.setTextColor(getColor(R.color.card_text_night))
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