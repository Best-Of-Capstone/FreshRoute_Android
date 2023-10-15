package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.yong.freshroute.R
import com.yong.freshroute.util.Enums
import com.yong.freshroute.util.LocationData

class SearchActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.search_toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val btnDetail = findViewById<MaterialButton>(R.id.btn_search_detail)
        val btnInputFrom = findViewById<LinearLayout>(R.id.btn_search_input_from)
        val btnInputTo = findViewById<LinearLayout>(R.id.btn_search_input_to)
        btnDetail.setOnClickListener(btnListener)
        btnInputFrom.setOnClickListener(btnListener)
        btnInputTo.setOnClickListener(btnListener)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val inputData: LocationData
                if(Build.VERSION.SDK_INT >= 33){
                    inputData = result.data!!.getSerializableExtra("data", LocationData::class.java)!!
                }else{
                    @Suppress("DEPRECATION")
                    inputData = result.data!!.getSerializableExtra("data") as LocationData
                }
                Toast.makeText(applicationContext, inputData.Name, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "Noting Inputted", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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