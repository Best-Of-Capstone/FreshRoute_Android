package com.yong.freshroute.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.yong.freshroute.R

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val btnDetail = findViewById<Button>(R.id.btn_search_detail)
        val btnInput = findViewById<Button>(R.id.btn_search_input)
        btnDetail.setOnClickListener(btnListener)
        btnInput.setOnClickListener(btnListener)
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_search_detail -> startActivity(Intent(applicationContext, DetailActivity::class.java))
            R.id.btn_search_input -> startActivity(Intent(applicationContext, SearchInputActivity::class.java))
        }
    }
}