package com.qndev.weatherforecast

import com.qndev.weatherforecast.data.remote.WeatherApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherApi: WeatherApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        weatherApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://api.openweathermap.org"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetWeather() {
        val apiKey = "019196fb4af032a27b4be150ce1fef32"
        val unit = "metric"
        val lat = 10.762622
        val long = 106.66017
        val response = weatherApi.getWeatherInfo(lat, long, unit, apiKey).execute()
        assert(response.isSuccessful)
        assert(response.body() != null)
    }


}
