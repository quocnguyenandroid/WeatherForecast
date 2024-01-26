package com.qndev.weatherforecast.data.model

data class WeatherData(
    val lat: String,
    val lon: String,
    val current: WeatherInfo,
    val daily: List<Daily>,
    val hourly: List<WeatherInfo>
)