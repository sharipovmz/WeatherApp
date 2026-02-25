package com.example.weatherapp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.usecase.LoadCityWeatherUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val loadCityWeatherUseCase: LoadCityWeatherUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    init {
        isLoad()
    }

    fun setCity(city: String) {
        _state.update { it.copy(city = city) }
    }

    fun isLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000)
            _state.update { it.copy(isLoading = false) }
        }
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
