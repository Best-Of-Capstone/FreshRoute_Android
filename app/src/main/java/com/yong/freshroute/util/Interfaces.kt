package com.yong.freshroute.util

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
    val elevationDelta: Number,
    val type: String,
    val name: String,
    val isWalking: Boolean,
    val wayPoints: List<Number>
): Serializable

data class RouteApiResultItemData(
    val ascent: Number,
    val descent: Number,
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