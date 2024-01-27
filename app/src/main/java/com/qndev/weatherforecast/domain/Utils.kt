package com.qndev.weatherforecast.domain

import android.content.Context
import android.content.SharedPreferences
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


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
    }
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
    return dateFormat.format(date)
}

fun getAssetJsonData(context: Context): String? {
    val json: String = try {
        val inputStream: InputStream = context.assets.open("vn.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun Context.sharedPreferences(name: String): SharedPreferences =
    getSharedPreferences("${name}_shared_preferences", Context.MODE_PRIVATE)
