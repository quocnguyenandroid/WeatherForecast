package com.qndev.weatherforecast.data.di

import com.qndev.weatherforecast.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.openweathermap.org"
    private const val REQUEST_TIME_OUT = 5

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }

}