package com.qndev.weatherforecast.domain.model

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

data class Temp(
    @SerializedName("day")
    val dayTemp: Float,
    @SerializedName("min")
    val minTemp: Float,
    @SerializedName("max")
    val maxTemp: Float
)