package com.qndev.weatherforecast.presentation.sharepref

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qndev.weatherforecast.data.model.City
import com.qndev.weatherforecast.domain.sharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val KEY_FAVORITE_CITIES = "favorite_cities"
    }

    private val sharedPreferences = context.sharedPreferences("favorite")
    private val gson = Gson()

    private fun saveFavorites(favoriteItems: List<City>) {
        sharedPreferences.edit {
            val json = gson.toJson(favoriteItems)
            putString(KEY_FAVORITE_CITIES, json)
        }
    }

    fun getFavorites(): List<City> {
        val json = sharedPreferences.getString(KEY_FAVORITE_CITIES, "")
        val type = object : TypeToken<List<City>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun toggleFavorite(city: City) {
        val favorites = getFavorites().toMutableList()

        if (favorites.contains(city)) {
            favorites.remove(city)
        } else {
            favorites.add(city)
        }
        saveFavorites(favorites)
    }

}