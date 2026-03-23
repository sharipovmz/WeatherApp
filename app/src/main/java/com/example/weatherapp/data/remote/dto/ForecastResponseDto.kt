package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Ответ 5 Day / 3 Hour Forecast API (2.5/forecast) — бесплатный. */
@Serializable
data class ForecastResponseDto(
    @SerialName("list") val list: List<ForecastItemDto> = emptyList()
)

@Serializable
data class ForecastItemDto(
    @SerialName("dt") val dt: Long = 0L,
    @SerialName("main") val main: MainDto? = null,
    @SerialName("weather") val weather: List<WeatherItemDto> = emptyList(),
    @SerialName("wind") val wind: WindDto? = null
)