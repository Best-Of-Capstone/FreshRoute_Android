package com.yong.freshroute.util

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class KakaoLocalItem(
    @SerializedName("place_name") val localName: String,
    @SerializedName("road_address_name") val localAddress: String,
    @SerializedName("x") val localLongitude: String,
    @SerializedName("y") val localLatitude: String
)

data class KakaoLocalList(
    @SerializedName("documents") val dataList: List<KakaoLocalItem>
)

interface KakaoLocalAPI {
    @GET("v2/local/search/keyword.JSON")
    fun getLocalList(
        @Header("Authorization") apiKey: String,
        @Query("query") queryString : String
    ): Call<KakaoLocalList>
}

interface RouteApi {
    @POST("findRouter")
    fun getRouteList(@Body coordData: RouteApiInput): Call<ApiResult<RouteApiResult>>
}