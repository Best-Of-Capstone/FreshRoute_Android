package com.yong.freshroute.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yong.freshroute.R
import com.yong.freshroute.util.RouteApiResultItem

class DetailActivity : AppCompatActivity() {
    var routeData: RouteApiResultItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if(Build.VERSION.SDK_INT >= 33){
            routeData = intent.getSerializableExtra("route_data", RouteApiResultItem::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            routeData = intent.getSerializableExtra("from") as RouteApiResultItem
        }

        Toast.makeText(applicationContext, routeData.toString(), Toast.LENGTH_LONG).show()
    }
}