package com.example.drivemate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivemate.api.WeatherResponse
import com.example.drivemate.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weather =
        MutableStateFlow<WeatherResponse?>(null)

    val weather: StateFlow<WeatherResponse?> =
        _weather

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    fun loadWeather(
        latitude: Double,
        longitude: Double
    ) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                _weather.value =
                    repository.getWeather(
                        latitude = latitude,
                        longitude = longitude
                    )

            } catch (e: Exception) {

                _error.value =
                    e.message ?: "Unable to load weather"

            } finally {

                _isLoading.value = false
            }
        }
    }
}