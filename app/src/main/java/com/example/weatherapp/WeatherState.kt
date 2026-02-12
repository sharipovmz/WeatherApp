package com.example.weatherapp

data class WeatherState(
    val city: String = "Los Angeles, CA",
    val temperature: Int = 72,
    val weather: String = "Sunny",
    val high: String = "H: 20",
    val low: String = "L: 11",
    val hForecast: List<HourlyWeather> = defaultHourlyForecast(),
    val progressBar: Boolean = false
)

data class HourlyWeather(
    val hour: String,
    val icon: String,
    val temp: String
)

private fun defaultHourlyForecast(): List<HourlyWeather> = listOf(
    HourlyWeather("Now", "cloud", "72"),
    HourlyWeather("1 PM", "cloud", "73"),
    HourlyWeather("2 PM", "cloud", "74"),
    HourlyWeather("3 PM", "cloud", "74"),
    HourlyWeather("4 PM", "cloud", "73"),
    HourlyWeather("5 PM", "cloud", "71")
)
