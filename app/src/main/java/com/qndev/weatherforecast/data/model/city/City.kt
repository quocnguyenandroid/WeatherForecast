package com.qndev.weatherforecast.data.model.city


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("admin_name")
    val adminName: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("iso2")
    val iso2: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("population")
    val population: String,
    @SerializedName("population_proper")
    val populationProper: String
) {
    override fun toString(): String {
        return city
    }
}