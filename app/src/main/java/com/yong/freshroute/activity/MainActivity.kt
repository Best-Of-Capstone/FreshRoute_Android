package com.yong.freshroute.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yong.freshroute.R
import com.yong.freshroute.util.ApiResult
import com.yong.freshroute.util.AuthUtil
import com.yong.freshroute.util.PermissionUtil.checkLocationPermission
import com.yong.freshroute.util.PermissionUtil.openAppInfo
import com.yong.freshroute.util.WeatherApiClient
import com.yong.freshroute.util.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            if(checkLocationPermission(applicationContext)) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { curLocation : Location? ->
                        if(curLocation != null) {
                            getWeather(curLocation.latitude.toString(), curLocation.longitude.toString())
                        }
                    }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(): Int {
        return SimpleDateFormat("HH").format(System.currentTimeMillis()).toInt()
    }

    private fun getWeather(lat: String, lng: String) {
        WeatherApiClient.WeatherApiService.getWeather(lat, lng).enqueue(
            object: Callback<ApiResult<WeatherApiResult>> {
                override fun onResponse(
                    call: Call<ApiResult<WeatherApiResult>>,
                    response: Response<ApiResult<WeatherApiResult>>
                ) {
                    if(response.isSuccessful.not()) {
                        Toast.makeText(applicationContext, String.format(getString(R.string.searchinput_noti_error), response.code().toString()), Toast.LENGTH_LONG).show()
                        return
                    } else {
                        updateWeatherAnim(response.body()!!.RESULT_DATA.id.toInt() / 100)
                        updateWeatherView(response.body()!!.RESULT_DATA.weather_msg.messages[0],
                            response.body()!!.RESULT_DATA.temp.toDouble(),
                            response.body()!!.RESULT_DATA.weather_msg.main)
                    }
                }

                override fun onFailure(call: Call<ApiResult<WeatherApiResult>>, t: Throwable) {
                    Toast.makeText(applicationContext, String.format(getString(R.string.searchinput_noti_error), t.message.toString()), Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun updateColorTheme() {
        if(getTime() in 6..17){
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

    private fun updateWeatherAnim(type: Int) {
        if(getTime() in 6..17) {
            when(type) {
                2-> mainAnimWeather.setAnimation(R.raw.anim_weather_storm)
                3-> mainAnimWeather.setAnimation(R.raw.anim_weather_rain)
                5-> mainAnimWeather.setAnimation(R.raw.anim_weather_rain)
                6-> mainAnimWeather.setAnimation(R.raw.anim_weather_snow)
                7-> mainAnimWeather.setAnimation(R.raw.anim_weather_fog)
                8-> mainAnimWeather.setAnimation(R.raw.anim_weather_sunny)
            }
        } else {
            Log.d("WEATHER", type.toString())
            when(type) {
                2-> mainAnimWeather.setAnimation(R.raw.anim_weather_storm_night)
                3-> mainAnimWeather.setAnimation(R.raw.anim_weather_rain_night)
                5-> mainAnimWeather.setAnimation(R.raw.anim_weather_rain_night)
                6-> mainAnimWeather.setAnimation(R.raw.anim_weather_snow_night)
                7-> mainAnimWeather.setAnimation(R.raw.anim_weather_fog_night)
                8-> mainAnimWeather.setAnimation(R.raw.anim_weather_sunny_night)
            }
        }
        mainAnimWeather.playAnimation()
    }

    private fun updateWeatherView(desc: String, temp: Double, type: String) {
        mainWeatherDescText.text = desc
        mainWeatherInfoText.text = type
        mainWeatherTempText.text = String.format("%.1f°C", temp)
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_main_search -> startActivity(Intent(applicationContext, SearchActivity::class.java))
        }
    }
}