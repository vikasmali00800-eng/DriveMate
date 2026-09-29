package com.example.drivemate.repository

import com.example.drivemate.api.RetrofitInstance
import com.example.drivemate.api.WeatherResponse

class WeatherRepository {

    private val api = RetrofitInstance.weatherApi

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {

        return api.getWeather(
            latitude = latitude,
            longitude = longitude
        )
    }
}