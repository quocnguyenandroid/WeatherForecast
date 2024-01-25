package com.qndev.weatherforecast.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import kotlin.math.roundToInt


enum class DateFormat {
    DATE_TIME,
    DATE,
    TIME
}

fun Long.toLocalTime(type: DateFormat): String {
    val date = Date(this * 1000L)
    val dateFormat = when (type) {
        DateFormat.DATE_TIME -> SimpleDateFormat("dd-MM-yyyy HH:mm")
        DateFormat.DATE -> SimpleDateFormat("dd-MM")
        DateFormat.TIME -> SimpleDateFormat("HH:mm")
        else -> SimpleDateFormat("dd-MM-yyyy HH:mm")
    }
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
    return dateFormat.format(date)
}

fun Float.toDegree() = this.roundToInt().toString() + "\u00B0"
