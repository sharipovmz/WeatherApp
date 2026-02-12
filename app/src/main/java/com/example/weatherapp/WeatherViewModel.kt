package com.example.weatherapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WeatherViewModel : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun setCity(city: String) {
        _state.update { it.copy(city = city) }
    }

    fun setWeather(weather: String) {
        _state.update { it.copy(weather = weather) }
    }

    fun setTemperature(temperature: Int) {
        _state.update { it.copy(temperature = temperature) }
    }

    fun setHL(high: String, low: String) {
        _state.update { it.copy(high = high, low = low) }
    }

    fun setHourlyForecast(hourly: List<HourlyWeather>) {
        _state.update { it.copy(hForecast = hourly) }
    }
}
