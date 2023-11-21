package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.yong.freshroute.R
import com.yong.freshroute.adapter.SearchRecyclerAdapter
import com.yong.freshroute.util.ApiResult
import com.yong.freshroute.util.KakaoLocalClient
import com.yong.freshroute.util.KakaoLocalList
import com.yong.freshroute.util.LocationData
import com.yong.freshroute.util.RouteApiClient
import com.yong.freshroute.util.RouteApiInput
import com.yong.freshroute.util.RouteApiResult
import com.yong.freshroute.util.RouteApiResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActivity : AppCompatActivity() {
    private var locationDataFrom: LocationData? = null
    private var locationDataTo: LocationData? = null

    private lateinit var tvInputFrom: TextView
    private lateinit var tvInputTo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val toolbar = findViewById<Toolbar>(R.id.searchresult_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.searchresult_toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tvInputFrom = findViewById(R.id.tv_searchresult_input_from)
        tvInputTo = findViewById(R.id.tv_searchresult_input_to)

        if(Build.VERSION.SDK_INT >= 33){
            locationDataFrom = intent.getSerializableExtra("from", LocationData::class.java)!!
            locationDataTo = intent.getSerializableExtra("to", LocationData::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            locationDataFrom = intent.getSerializableExtra("from") as LocationData
            @Suppress("DEPRECATION")
            locationDataTo = intent.getSerializableExtra("to") as LocationData
        }

        tvInputFrom.text = locationDataFrom!!.Name
        tvInputTo.text = locationDataTo!!.Name

        val fromCoord: Array<Number> = arrayOf(locationDataFrom!!.Latitude, locationDataFrom!!.Longitude)
        val toCoord: Array<Number> = arrayOf(locationDataTo!!.Latitude, locationDataTo!!.Longitude)

        val locationData = RouteApiInput(fromCoord, toCoord, null)
        RouteApiClient.RouteApiService
            .getRouteList(locationData)
            .enqueue(object: Callback<ApiResult<RouteApiResult>> {
                override fun onResponse(call: Call<ApiResult<RouteApiResult>>, response: Response<ApiResult<RouteApiResult>>){
                    if(response.isSuccessful.not()) {
                        Toast.makeText(applicationContext, String.format(getString(R.string.searchinput_noti_error), response.code().toString()), Toast.LENGTH_LONG).show()
                        return
                    }else{
                        response.body()!!.RESULT_DATA.routeList.forEach {
                            Log.d("Route Results", it.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResult<RouteApiResult>>, t: Throwable) {
                    Toast.makeText(applicationContext, String.format(getString(R.string.searchinput_noti_error), t.message.toString()), Toast.LENGTH_LONG).show()
                }
            })
    }
}