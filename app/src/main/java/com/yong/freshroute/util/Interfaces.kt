package com.yong.freshroute.util

import com.google.gson.annotations.SerializedName
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

data class KakaoLocalList(
    @SerializedName("documents") val dataList: List<KakaoLocalItem>
)

data class KakaoLocalItem(
    @SerializedName("place_name") val localName: String,
    @SerializedName("road_address_name") val localAddress: String,
    @SerializedName("x") val localLongitude: String,
    @SerializedName("y") val localLatitude: String
)