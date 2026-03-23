package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.Coordinates

interface CityRepository {
    suspend fun searchCities(query: String): List<City>
    suspend fun loadCityWeather(city: String): Result<Unit>
    suspend fun loadCityCoordinates(city: String): Result<Coordinates>
    fun getPopularCities(): List<City>
    fun getSearchHistory(): List<City>
}
