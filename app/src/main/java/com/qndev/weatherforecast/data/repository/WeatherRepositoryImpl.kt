package com.qndev.weatherforecast.data.repository

import com.qndev.weatherforecast.data.API_KEY
import com.qndev.weatherforecast.data.UNIT
import com.qndev.weatherforecast.data.model.WeatherData
import com.qndev.weatherforecast.data.remote.WeatherApi
import com.qndev.weatherforecast.domain.repository.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        onResult: (isSuccess: Boolean, response: WeatherData?) -> Unit
    ) {
        weatherApi.getWeatherInfo(lat, lon, UNIT, API_KEY).enqueue(object : Callback<WeatherData> {
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                onResult(false, null)
            }

            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.body() != null && response.isSuccessful) {
                    onResult(true, response.body())
                } else {
                    onResult(false, null)
                }
            }
        })
    }
}