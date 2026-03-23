package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponseDto(
    @SerialName("main") val main: MainDto? = null,
    @SerialName("wind") val wind: WindDto? = null,
    @SerialName(    "weather") val weather: List<WeatherItemDto> = emptyList()
)

@Serializable
data class MainDto(
    @SerialName("temp") val temp: Double = 0.0,
    @SerialName("feels_like") val feelsLike: Double = 0.0,
    @SerialName("humidity") val humidity: Int = 0
)

@Serializable
data class WindDto(
    @SerialName("speed") val speed: Double = 0.0
)
