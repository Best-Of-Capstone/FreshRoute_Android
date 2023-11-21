package com.yong.freshroute.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.yong.freshroute.R
import com.yong.freshroute.util.LocationData

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
    }
}