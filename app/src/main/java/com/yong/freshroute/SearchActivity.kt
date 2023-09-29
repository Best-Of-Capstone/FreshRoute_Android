package com.yong.freshroute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val btnDetail = findViewById<Button>(R.id.btn_search_detail)
        btnDetail.setOnClickListener(btnListener)
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_search_detail -> Toast.makeText(applicationContext, "Detail Button", Toast.LENGTH_LONG).show()
        }
    }
}