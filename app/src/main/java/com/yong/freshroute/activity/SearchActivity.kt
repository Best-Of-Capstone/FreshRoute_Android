package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.yong.freshroute.R
import com.yong.freshroute.util.LocationData
import com.yong.freshroute.util.SearchTypes

class SearchActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var locationDataFrom: LocationData
    private lateinit var locationDataTo: LocationData

    private lateinit var tvInputFrom: TextView
    private lateinit var tvInputTo: TextView

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

        tvInputFrom = findViewById(R.id.tv_search_input_from)
        tvInputTo = findViewById(R.id.tv_search_input_to)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val inputData: LocationData
                val inputType: SearchTypes
                if(Build.VERSION.SDK_INT >= 33){
                    inputData = result.data!!.getSerializableExtra("data", LocationData::class.java)!!
                    inputType = result.data!!.getSerializableExtra("type", SearchTypes::class.java)!!
                }else{
                    @Suppress("DEPRECATION")
                    inputData = result.data!!.getSerializableExtra("data") as LocationData
                    @Suppress("DEPRECATION")
                    inputType = result.data!!.getSerializableExtra("type") as SearchTypes
                }

                if(inputType == SearchTypes.SEARCH_INPUT_FROM){
                    locationDataFrom = inputData
                    tvInputFrom.text = locationDataFrom.Name
                }else{
                    locationDataTo = inputData
                    tvInputTo.text = locationDataTo.Name
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> this.onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_search_detail -> startActivity(Intent(applicationContext, DetailActivity::class.java))
            R.id.btn_search_input_from -> {
                intent = Intent(applicationContext, SearchInputActivity::class.java)
                intent.putExtra("type", SearchTypes.SEARCH_INPUT_FROM)
                activityResultLauncher.launch(intent)
            }
            R.id.btn_search_input_to -> {
                intent = Intent(applicationContext, SearchInputActivity::class.java)
                intent.putExtra("type", SearchTypes.SEARCH_INPUT_TO)
                activityResultLauncher.launch(intent)
            }
        }
    }
}