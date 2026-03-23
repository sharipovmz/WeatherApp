package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherItemDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("main") val main: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("icon") val icon: String = ""
)
