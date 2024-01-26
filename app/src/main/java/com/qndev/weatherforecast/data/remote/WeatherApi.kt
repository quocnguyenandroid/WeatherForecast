package com.qndev.weatherforecast.data.remote

import com.qndev.weatherforecast.data.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/onecall")
    fun getWeatherInfo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): Call<WeatherData>
}