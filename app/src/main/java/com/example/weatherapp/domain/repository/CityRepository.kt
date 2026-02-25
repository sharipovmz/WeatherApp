package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.City

interface CityRepository {
    suspend fun searchCities(query: String): List<City>
    suspend fun loadCityWeather(city: String): Result<Unit>
    fun getPopularCities(): List<City>
    fun getSearchHistory(): List<City>
}
