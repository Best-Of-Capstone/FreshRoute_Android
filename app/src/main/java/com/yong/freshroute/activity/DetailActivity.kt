package com.yong.freshroute.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        initRouteDetail()
    }

    private fun initRouteDetail() {
        val recyclerAdapter = RouteDetailRecyclerAdapter(routeData!!.route.steps.toList())
        detailRecyclerView.adapter = recyclerAdapter
        detailRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerAdapter.itemClick = object: RouteDetailRecyclerAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(applicationContext, "Clicked ${routeData!!.route.coordinates[position][0]} ${routeData!!.route.coordinates[position][1]}", Toast.LENGTH_LONG).show()
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
                moveCamera(kakaoMap, routeData!!.route.coordinates[0][0], routeData!!.route.coordinates[0][1])
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

    private fun moveCamera(kakaoMap: KakaoMap, lat: Number, long: Number) {
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(lat.toDouble(), long.toDouble()))
        kakaoMap.moveCamera(cameraUpdate)
    }
}