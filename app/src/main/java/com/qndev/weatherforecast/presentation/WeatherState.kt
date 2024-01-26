package com.qndev.weatherforecast.presentation

import com.qndev.weatherforecast.data.model.WeatherData

data class WeatherState(
    val weatherData: WeatherData? = null,
    val state: State = State.INIT
)

enum class State {
    INIT,
    LOADING,
    SUCCESS,
    FAIL
}
