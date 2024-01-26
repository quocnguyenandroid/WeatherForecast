package com.qndev.weatherforecast.domain.repository

import com.qndev.weatherforecast.data.model.WeatherData

interface WeatherRepository {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        onResult: (isSuccess: Boolean, response: WeatherData?) -> Unit
    )
}