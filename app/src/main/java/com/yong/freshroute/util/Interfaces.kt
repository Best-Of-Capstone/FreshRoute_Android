package com.yong.freshroute.util

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.Serializable

data class LocationData(
    var Name: String,
    var Address: String,
    var Latitude: Double,
    var Longitude: Double
): Serializable

data class UserInfo(
    var Name: String,
    var Email: String,
    var UID: String
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

data class ApiResult<T>(
    val RESULT_CODE: Int,
    val RESUMT_MSG: String,
    val RESULT_DATA: T
)

data class RouteApiInput(
    val startCord: Array<Number>,
    val endCord: Array<Number>,
    val targetCount: Int?
)

data class RouteApiResultItemDataStep(
    val distance: Number,
    val duration: Number,
    val type: String,
    val name: String,
    val wayPoints: List<Number>
): Serializable

data class RouteApiResultItemData(
    val distance: String,
    val duration: String,
    val steps: Array<RouteApiResultItemDataStep>,
    val coordinates: Array<Array<Number>>
): Serializable

data class RouteApiResultItem(
    val id: Int,
    val description: String,
    val route: RouteApiResultItemData
): Serializable

data class RouteApiResult(
    val routeList: Array<RouteApiResultItem>,
)

interface RouteApi {
    @POST("findRouter")
    fun getRouteList(@Body coordData: RouteApiInput): Call<ApiResult<RouteApiResult>>
}