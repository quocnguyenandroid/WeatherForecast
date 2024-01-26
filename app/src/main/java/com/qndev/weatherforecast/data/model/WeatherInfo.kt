package com.qndev.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo(

    @SerializedName("dt")
    val time: Long,

    val temp: Float,

    @SerializedName("feels_like")
    val realFeel: Float,

    val humidity: Int,

    @SerializedName("wind_speed")
    val windSpeed: Float,

    @SerializedName("weather")
    val description: List<WeatherDescription>

)


