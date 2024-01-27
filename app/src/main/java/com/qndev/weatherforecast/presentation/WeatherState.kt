package com.qndev.weatherforecast.presentation

import com.qndev.weatherforecast.data.model.WeatherData

data class WeatherState(
    val weatherData: WeatherData? = null,
    val state: State? = null
)

enum class State {
    LOADING,
    SUCCESS,
    FAIL
}
