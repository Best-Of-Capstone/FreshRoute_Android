package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.yong.freshroute.R
import com.yong.freshroute.util.Enums
import com.yong.freshroute.util.LocationData

class SearchInputActivity : AppCompatActivity() {
    private lateinit var btnSearch: MaterialButton
    private lateinit var edSearchKeyword: TextInputEditText

    private lateinit var inputData: LocationData
    private lateinit var inputType: Enums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_input)

        btnSearch = findViewById(R.id.btn_searchinput_search)
        edSearchKeyword = findViewById(R.id.et_searchinput_text)
        btnSearch.setOnClickListener(btnListener)

        if(Build.VERSION.SDK_INT >= 33){
            inputType = intent.getSerializableExtra("type", Enums::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            inputType = intent.getSerializableExtra("type") as Enums
        }

        this.onBackPressedDispatcher.addCallback(this) {
            onBackListener()
        }
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_searchinput_search -> {
                inputData = LocationData("Name", "Address", 1.0, 1.0)

                val resultIntent = Intent(applicationContext, SearchActivity::class.java)
                resultIntent.putExtra("data", inputData)
                resultIntent.putExtra("type", inputType)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun onBackListener() {
        val resultIntent = Intent(applicationContext, SearchActivity::class.java)
        resultIntent.putExtra("type", inputType)
        setResult(RESULT_CANCELED, resultIntent)
        finish()
    }
}