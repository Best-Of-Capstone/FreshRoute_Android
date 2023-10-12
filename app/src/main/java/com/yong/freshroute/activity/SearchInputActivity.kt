package com.yong.freshroute.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yong.freshroute.R

class SearchInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_input)
    }

    override fun onDestroy() {
        super.onDestroy()
        val resultIntent = Intent()
        resultIntent.putExtra("input", "chungang-univ")
        setResult(RESULT_OK, resultIntent)
    }
}