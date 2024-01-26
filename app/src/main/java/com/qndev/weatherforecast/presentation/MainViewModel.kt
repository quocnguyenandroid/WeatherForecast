package com.qndev.weatherforecast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qndev.weatherforecast.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {


    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: Flow<WeatherState> = _weatherState

    fun getCurrentWeather(lat: Double, lon: Double) {
        _weatherState.update { it.copy(state = State.LOADING) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentWeather(lat, lon) { isSuccess, response ->
                if (isSuccess) {
                    _weatherState.update {
                        it.copy(weatherData = response, state = State.SUCCESS)
                    }
                } else {
                    _weatherState.update {
                        it.copy(state = State.FAIL)
                    }
                }
            }
        }
    }
}