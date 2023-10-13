package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yong.freshroute.R
import com.yong.freshroute.util.Enums

class SearchInputActivity : AppCompatActivity() {
    private lateinit var inputType: Enums
    var inputData = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_input)

        if(Build.VERSION.SDK_INT >= 33){
            inputType = intent.getSerializableExtra("type", Enums::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            inputType = intent.getSerializableExtra("type") as Enums
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent(applicationContext, SearchActivity::class.java)
        resultIntent.putExtra("type", inputType)
        if(inputData.length > 0){
            resultIntent.putExtra("input", "chungang-univ")
            setResult(RESULT_OK, resultIntent)
        }else{
            setResult(RESULT_CANCELED, resultIntent)
        }
        finish()
    }
}