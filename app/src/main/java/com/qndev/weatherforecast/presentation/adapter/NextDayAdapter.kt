package com.qndev.weatherforecast.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.qndev.weatherforecast.R
import com.qndev.weatherforecast.data.model.Daily
import com.qndev.weatherforecast.databinding.ViewholderNextDayBinding
import com.qndev.weatherforecast.domain.DateFormat
import com.qndev.weatherforecast.domain.toLocalTime

class NextDayAdapter : RecyclerView.Adapter<NextDayAdapter.NextDayViewHolder>() {
    private var forecastList: List<Daily> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextDayViewHolder {
        val binding = ViewholderNextDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NextDayViewHolder(binding)
    }

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: NextDayViewHolder, position: Int) {
        holder.bind(forecastList[position])
    }

    class NextDayViewHolder(private val binding: ViewholderNextDayBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(forecast: Daily) {
            binding.highLowTempTv.text = "H:${forecast.temp.maxTemp.toInt()} L:${forecast.temp.minTemp.toInt()}"
            binding.dayTv.text = forecast.time.toLocalTime(DateFormat.DATE)
            binding.humidityTv.text = forecast.humidity.toString() + "%"
            binding.windSpeedTv.text = forecast.windSpeed.toString() + "km/h"
            val iconId = forecast.description[0].icon
            val imageUrl = "http://openweathermap.org/img/wn/${iconId}@4x.png"
            binding.descriptionImg.load(imageUrl) {
                placeholder(R.drawable.cloudy_sunny)
                error(R.drawable.cloudy_sunny)
            }
        }
    }

    fun updateForecastList(forecastList: List<Daily>) {
        this.forecastList = forecastList
        notifyDataSetChanged()
    }
}