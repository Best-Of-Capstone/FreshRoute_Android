package com.yong.freshroute.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import com.yong.freshroute.R
import com.yong.freshroute.adapter.RouteDetailRecyclerAdapter
import com.yong.freshroute.util.RouteApiResultItem

class DetailActivity : AppCompatActivity() {
    private lateinit var detailMapView: MapView
    private lateinit var detailRecyclerView: RecyclerView

    var routeData: RouteApiResultItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailMapView = findViewById(R.id.map_detail_view)
        detailRecyclerView = findViewById(R.id.recycler_detail_route)

        if(Build.VERSION.SDK_INT >= 33){
            routeData = intent.getSerializableExtra("route_data", RouteApiResultItem::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            routeData = intent.getSerializableExtra("from") as RouteApiResultItem
        }

        initMapView()
    }

    private fun initRouteDetail(kakaoMap: KakaoMap) {
        val recyclerAdapter = RouteDetailRecyclerAdapter(routeData!!.route.steps.toList())
        detailRecyclerView.adapter = recyclerAdapter
        detailRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerAdapter.itemClick = object: RouteDetailRecyclerAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val coordIdx = routeData!!.route.steps[position].wayPoints[0].toInt()
                moveCamera(kakaoMap, routeData!!.route.coordinates[coordIdx][0], routeData!!.route.coordinates[coordIdx][1])
            }
        }
    }

    private fun initMapView() {
        detailMapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {}
            override fun onMapError(error: Exception) {}
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                drawRoute(kakaoMap)
                initRouteDetail(kakaoMap)
                moveCamera(kakaoMap, routeData!!.route.coordinates[0][0], routeData!!.route.coordinates[0][1])
            }
        })
    }

    private fun drawRoute(kakaoMap: KakaoMap) {
        val routeLayer = kakaoMap.routeLineManager!!.layer

        val routeStyleBlack = RouteLineStyles.from(RouteLineStyle.from(15f, getColor(android.R.color.black)))
        val routeStyleBlue = RouteLineStyles.from(RouteLineStyle.from(15f, getColor(R.color.route_blue)))
        val routeStyleGreen = RouteLineStyles.from(RouteLineStyle.from(15f, getColor(R.color.route_green)))
        val routeStyleOrange = RouteLineStyles.from(RouteLineStyle.from(15f, getColor(R.color.route_orange)))
        val routeStyleRed = RouteLineStyles.from(RouteLineStyle.from(15f, getColor(R.color.route_red)))
        val routeStyleYellow = RouteLineStyles.from(RouteLineStyle.from(15f, getColor(R.color.route_yellow)))
        val routeStyleSet = RouteLineStylesSet.from(routeStyleBlack, routeStyleBlue, routeStyleGreen, routeStyleYellow, routeStyleOrange, routeStyleRed)

        val routeLineList: MutableList<RouteLineSegment> = mutableListOf()
        routeData!!.route.steps.forEach {
            val styleIdx = if (it.isWalking) getRouteStyleIndex(it.distance.toDouble(), it.elevationDelta.toDouble())
                            else 0
            val routeCordList: MutableList<LatLng> = mutableListOf()
            for(i in it.wayPoints[0].toInt() .. it.wayPoints[1].toInt()){
                routeCordList.add(
                    LatLng.from(routeData!!.route.coordinates[i][0].toDouble(),
                        routeData!!.route.coordinates[i][1].toDouble()))
            }
            if(routeCordList.size == 1){
                routeCordList.add(routeCordList[0])
            }
            routeLineList.add(RouteLineSegment.from(routeCordList, routeStyleSet.getStyles(styleIdx)))
        }

        val routeOptions = RouteLineOptions.from(routeLineList).setStylesSet(routeStyleSet)
        routeLayer.addRouteLine(routeOptions)
    }

    private fun getRouteStyleIndex(distance: Double, elvDelta: Double): Int{
        val incline = kotlin.math.abs(100 * elvDelta / distance)
        if(incline >= 7){
            return 5
        }else if(incline >= 5){
            return 4
        }else if(incline >= 3){
            return 3
        }else if(incline >= 1){
            return 2
        }
        return 1
    }

    private fun moveCamera(kakaoMap: KakaoMap, lat: Number, long: Number) {
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(lat.toDouble(), long.toDouble()))
        kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true))
    }
}