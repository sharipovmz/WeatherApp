package com.example.weatherapp.domain.model

data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyForecast> = emptyList(),
    val daily: List<DailyForecast> = emptyList()
)

data class CurrentWeather(
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String
)

data class HourlyForecast(
    val dt: Long,
    val temp: Double,
    val feelsLike: Double,
    val description: String,
    val icon: String
)

data class DailyForecast(
    val dt: Long,
    val tempMin: Double,
    val tempMax: Double,
    val description: String,
    val icon: String
)

