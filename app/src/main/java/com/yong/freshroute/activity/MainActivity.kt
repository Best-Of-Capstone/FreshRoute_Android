package com.yong.freshroute.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.yong.freshroute.R
import com.yong.freshroute.util.AuthUtil
import com.yong.freshroute.util.PermissionUtil.checkLocationPermission
import com.yong.freshroute.util.PermissionUtil.openAppInfo

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mainBtnSearch: LinearLayout
    private lateinit var mainMapView: MapView
    private lateinit var mainWeatherText: TextView
    private lateinit var mainWelcomeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBtnSearch = findViewById(R.id.btn_main_search)
        mainBtnSearch.setOnClickListener(btnListener)

        mainMapView = findViewById(R.id.map_main_view)

        mainWeatherText = findViewById(R.id.tv_main_weather)
        mainWelcomeText = findViewById(R.id.tv_main_welcome)

        mainWeatherText.text = getString(R.string.main_tv_weather_default)
        mainWelcomeText.text = String.format(getString(R.string.main_tv_welcome_format), AuthUtil.getUserInfo()!!.Name)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    override fun onResume() {
        super.onResume()

        if(!checkLocationPermission(applicationContext)) {
            openAppInfo(applicationContext)
        } else {
            mainMapView.start(object : MapLifeCycleCallback() {
                override fun onMapDestroy() {}

                override fun onMapError(error: Exception) {}
            }, object : KakaoMapReadyCallback() {
                override fun onMapReady(kakaoMap: KakaoMap) {
                    if(checkLocationPermission(applicationContext)) {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { curLocation : Location? ->
                                if(curLocation != null){
                                    val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(curLocation.latitude, curLocation.longitude))
                                    kakaoMap.moveCamera(cameraUpdate)
                                }
                            }
                    }
                }
            })
        }
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_main_search -> startActivity(Intent(applicationContext, SearchActivity::class.java))
        }
    }
}