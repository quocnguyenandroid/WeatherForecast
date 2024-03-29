package com.qndev.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("dt")
    val time: Long,
    val temp: Temp,
    @SerializedName("weather")
    val description: List<WeatherDescription>,
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float
)