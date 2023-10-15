package com.yong.freshroute.util

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

data class LocationData(
    var Name: String,
    var Address: String,
    var Latitude: Number,
    var Longitude: Number
): Serializable

enum class SearchTypes {
    SEARCH_INPUT_FROM,
    SEARCH_INPUT_TO
}

interface KakaoLocalAPI {
    @GET("v2/local/search/keyword.JSON")
    fun getLocalList(
        @Header("Authorization") apiKey: String,
        @Query("query") queryString : String
    ): Call<KakaoLocalList>
}

data class KakaoLocalList(
    @SerializedName("documents") val dataList: List<KakaoLocalItem>
)

data class KakaoLocalItem(
    @SerializedName("place_name") val localName: String,
    @SerializedName("road_address_name") val localAddress: String,
    @SerializedName("x") val localLongitude: String,
    @SerializedName("y") val localLatitude: String
)