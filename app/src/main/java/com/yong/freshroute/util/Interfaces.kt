package com.yong.freshroute.util

import java.io.Serializable

data class LocationData(
    var Name: String,
    var Address: String,
    var Latitude: Number,
    var Longitude: Number
): Serializable