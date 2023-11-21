package com.yong.freshroute.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.yong.freshroute.R
import com.yong.freshroute.adapter.SearchRecyclerAdapter
import com.yong.freshroute.util.KakaoLocalClient
import com.yong.freshroute.util.KakaoLocalList
import com.yong.freshroute.util.LocationData
import com.yong.freshroute.util.SearchTypes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchInputActivity : AppCompatActivity() {
    private lateinit var btnSearch: MaterialButton
    private lateinit var edSearchKeyword: TextInputEditText
    private lateinit var recyclerSearchResult: RecyclerView

    private lateinit var inputData: LocationData
    private lateinit var inputType: SearchTypes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_input)

        val toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.search_toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnSearch = findViewById(R.id.btn_searchinput_search)
        edSearchKeyword = findViewById(R.id.et_searchinput_text)
        recyclerSearchResult = findViewById(R.id.recycler_searchinput_result)

        btnSearch.setOnClickListener(btnListener)

        if(Build.VERSION.SDK_INT >= 33){
            inputType = intent.getSerializableExtra("type", SearchTypes::class.java)!!
        }else{
            @Suppress("DEPRECATION")
            inputType = intent.getSerializableExtra("type") as SearchTypes
        }

        this.onBackPressedDispatcher.addCallback(this) {
            onBackListener()
        }
    }

    private val btnListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.btn_searchinput_search -> {
                val inputStr = edSearchKeyword.text.toString()
                if(inputStr.isEmpty()){
                    Toast.makeText(applicationContext, getString(R.string.searchinput_noti_wrong_input), Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }

                KakaoLocalClient.kakaoLocalService
                    .getLocalList("KakaoAK ${getString(R.string.KAKAO_REST_API_KEY)}", inputStr)
                    .enqueue(object: Callback<KakaoLocalList> {
                        override fun onResponse(call: Call<KakaoLocalList>, response: Response<KakaoLocalList>){
                            if(response.isSuccessful.not()) {
                                Toast.makeText(applicationContext, String.format(getString(R.string.searchinput_noti_error), response.code().toString()), Toast.LENGTH_LONG).show()
                                return
                            }else{
                                val resultList = response.body()!!.dataList
                                if(resultList.isEmpty()){
                                    Toast.makeText(applicationContext, getString(R.string.searchinput_noti_no_result), Toast.LENGTH_LONG).show()
                                    return
                                }

                                val recyclerAdapter = SearchRecyclerAdapter(resultList)
                                recyclerSearchResult.adapter = recyclerAdapter
                                recyclerSearchResult.layoutManager = LinearLayoutManager(applicationContext)
                                recyclerAdapter.itemClick = object: SearchRecyclerAdapter.ItemClick {
                                    override fun onClick(view: View, position: Int) {
                                        inputData = LocationData(resultList[position].localName,
                                            resultList[position].localAddress,
                                            resultList[position].localLatitude.toDouble(),
                                            resultList[position].localLongitude.toDouble())

                                        val resultIntent = Intent(applicationContext, SearchActivity::class.java)
                                        resultIntent.putExtra("data", inputData)
                                        resultIntent.putExtra("type", inputType)
                                        setResult(RESULT_OK, resultIntent)
                                        finish()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<KakaoLocalList>, t: Throwable) {
                            Toast.makeText(applicationContext, String.format(getString(R.string.searchinput_noti_error), t.message.toString()), Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
    }

    private fun onBackListener() {
        val resultIntent = Intent(applicationContext, SearchActivity::class.java)
        resultIntent.putExtra("type", inputType)
        setResult(RESULT_CANCELED, resultIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> this.onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}