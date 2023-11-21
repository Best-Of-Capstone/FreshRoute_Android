package com.yong.freshroute.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import com.yong.freshroute.R
import com.yong.freshroute.util.RouteApiResultItem

class DetailActivity : AppCompatActivity() {
    private lateinit var detailMapView: MapView

    var routeData: RouteApiResultItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailMapView = findViewById(R.id.map_detail_view)

        if(Build.VERSION.SDK_INT >= 33){
            routeData = intent.getSerializableExtra("route_data", RouteApiResultItem::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            routeData = intent.getSerializableExtra("from") as RouteApiResultItem
        }

        initMapView()
    }

    private fun initMapView() {
        detailMapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {}
            override fun onMapError(error: Exception) {}
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                if(routeData != null){
                    val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(routeData!!.route.coordinates[0][0].toDouble(), routeData!!.route.coordinates[0][1].toDouble()))
                    kakaoMap.moveCamera(cameraUpdate)

                    drawRoute(kakaoMap)
                }
            }
        })
    }

    private fun drawRoute(kakaoMap: KakaoMap) {
        val routeLayer = kakaoMap.routeLineManager!!.layer
        val routeStyle = RouteLineStylesSet.from("blueStyles", RouteLineStyles.from(RouteLineStyle.from(15f, getColor(R.color.cau_blue))))

        val routeSegment = RouteLineSegment.from(
            routeData!!.route.coordinates.map {
                LatLng.from(it[0].toDouble(), it[1].toDouble())
            })
            .setStyles(routeStyle.getStyles(0))

        val routeOptions = RouteLineOptions.from(routeSegment).setStylesSet(routeStyle)
        routeLayer.addRouteLine(routeOptions)
    }
}