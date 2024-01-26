package com.qndev.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("day")
    val dayTemp: Float,
    @SerializedName("min")
    val minTemp: Float,
    @SerializedName("max")
    val maxTemp: Float
)
