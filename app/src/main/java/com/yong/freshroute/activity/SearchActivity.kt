package com.yong.freshroute.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.yong.freshroute.R
import com.yong.freshroute.util.Enums

class SearchActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val btnDetail = findViewById<Button>(R.id.btn_search_detail)
        val btnInputFrom = findViewById<Button>(R.id.btn_search_input_from)
        val btnInputTo = findViewById<Button>(R.id.btn_search_input_to)
        btnDetail.setOnClickListener(btnListener)
        btnInputFrom.setOnClickListener(btnListener)
        btnInputTo.setOnClickListener(btnListener)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val inputStr = result.data!!.getStringExtra("input")
                Toast.makeText(applicationContext, inputStr, Toast.LENGTH_LONG).show()
            }
        }
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_search_detail -> startActivity(Intent(applicationContext, DetailActivity::class.java))
            R.id.btn_search_input_from -> {
                intent = Intent(applicationContext, SearchInputActivity::class.java)
                intent.putExtra("type", Enums.SEARCH_INPUT_FROM)
                activityResultLauncher.launch(intent)
            }
            R.id.btn_search_input_to -> {
                intent = Intent(applicationContext, SearchInputActivity::class.java)
                intent.putExtra("type", Enums.SEARCH_INPUT_TO)
                activityResultLauncher.launch(intent)
            }
        }
    }
}