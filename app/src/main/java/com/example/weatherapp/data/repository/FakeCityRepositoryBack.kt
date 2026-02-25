package com.example.weatherapp.data.repository

import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.delay

class FakeCityRepositoryBack : CityRepository {

    private val allCities = listOf(
        City("San Francisco, CA", "US"),
        City("New York, NY", "US"),
        City("Los Angeles, CA", "US"),
        City("Chicago, IL", "US"),
        City("Miami, FL", "US"),
        City("Seattle, WA", "US"),
        City("Austin, TX", "US")
    )

    private val history = mutableListOf(
        City("Los Angeles, CA", "US"),
        City("New York, NY", "US")
    )

    override suspend fun searchCities(query: String): List<City> {
        delay(1000)
        return allCities.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    override suspend fun loadCityWeather(city: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    override fun getPopularCities(): List<City> = allCities

    override fun getSearchHistory(): List<City> = history
}
