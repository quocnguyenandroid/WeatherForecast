package com.qndev.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherDescription(

    @SerializedName("main")
    val description: String,
    val icon: String
)