package com.yong.freshroute.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.yong.freshroute.R

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val toolbar = findViewById<Toolbar>(R.id.searchresult_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.searchresult_toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}