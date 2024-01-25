package com.qndev.weatherforecast.domain.model

import com.google.gson.annotations.SerializedName

data class WeatherDescription(

    @SerializedName("main")
    val description: String,
    val icon: String
)