package com.yong.freshroute.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.preference.PreferenceManager
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.yong.freshroute.R
import com.yong.freshroute.util.PermissionUtil.checkLocationPermission
import com.yong.freshroute.util.PermissionUtil.openAppInfo

class MainActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences

    private lateinit var mainBtnSearch: AppCompatButton
    private lateinit var mainMapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if(pref.getBoolean("isFirst", true)) {
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
        }

        mainBtnSearch = findViewById(R.id.btn_main_search)
        mainBtnSearch.setOnClickListener(btnListener)

        mainMapView = findViewById(R.id.map_main_view)
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
                override fun onMapReady(kakaoMap: KakaoMap) {}
            })
        }
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_main_search -> startActivity(Intent(applicationContext, SearchActivity::class.java))
        }
    }
}