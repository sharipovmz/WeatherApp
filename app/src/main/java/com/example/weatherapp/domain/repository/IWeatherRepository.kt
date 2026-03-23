package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.Weather

interface IWeatherRepository {

    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): Result<Weather>
}
