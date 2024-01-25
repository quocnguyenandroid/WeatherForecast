package com.qndev.weatherforecast.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qndev.weatherforecast.databinding.ActivityMainBinding
import com.qndev.weatherforecast.domain.model.Daily
import com.qndev.weatherforecast.domain.model.Temp
import com.qndev.weatherforecast.domain.model.WeatherDescription
import com.qndev.weatherforecast.presentation.adapter.NextDayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = NextDayAdapter()
        activityMainBinding.rcvForecast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityMainBinding.rcvForecast.adapter = adapter
        adapter.updateForecastList(
            listOf(
                Daily(
                    time = 1684951200,
                    temp = Temp(25F, 25F, 25F),
                    description = listOf(WeatherDescription("none", "error")),
                    humidity = 20,
                    windSpeed = 25F
                ),
                Daily(
                    time = 1684951200,
                    temp = Temp(25F, 25F, 25F),
                    description = listOf(WeatherDescription("none", "error")),
                    humidity = 20,
                    windSpeed = 25F
                ),
                Daily(
                    time = 1684951200,
                    temp = Temp(25F, 25F, 25F),
                    description = listOf(WeatherDescription("none", "error")),
                    humidity = 20,
                    windSpeed = 25F
                )
            )
        )
    }
}