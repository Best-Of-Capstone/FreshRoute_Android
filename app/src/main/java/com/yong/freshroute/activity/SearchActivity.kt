package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.slider.Slider
import com.yong.freshroute.R
import com.yong.freshroute.util.LocationData
import com.yong.freshroute.util.SearchTypes

class SearchActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var locationDataFrom: LocationData? = null
    private var locationDataTo: LocationData? = null

    private lateinit var btnTransportation: MaterialButtonToggleGroup
    private lateinit var sliderCongestion: Slider
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

        btnTransportation = findViewById(R.id.toggle_search_fresh_transportation)
        sliderCongestion = findViewById(R.id.slider_search_fresh_congestion)
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
                    tvInputFrom.text = locationDataFrom!!.Name
                }else{
                    locationDataTo = inputData
                    tvInputTo.text = locationDataTo!!.Name
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
            R.id.btn_search_detail -> {
                if(locationDataFrom != null && locationDataTo != null
                    && locationDataFrom!!.Name.isNotEmpty() && locationDataTo!!.Name.isNotEmpty()){
                    val searchIntent = Intent(applicationContext, SearchResultActivity::class.java)
                    searchIntent.putExtra("from", locationDataFrom)
                    searchIntent.putExtra("to", locationDataTo)

                    searchIntent.putExtra("congestion", sliderCongestion.value)
                    when(btnTransportation.checkedButtonId) {
                        R.id.btn_search_transportation_all -> searchIntent.putExtra("transportation", 0)
                        R.id.btn_search_transportation_bus -> searchIntent.putExtra("transportation", 1)
                        R.id.btn_search_transportation_subway -> searchIntent.putExtra("transportation", 2)
                    }

                    startActivity(searchIntent)
                }else{
                    Toast.makeText(applicationContext, "Empty Input", Toast.LENGTH_LONG).show()
                }
            }
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