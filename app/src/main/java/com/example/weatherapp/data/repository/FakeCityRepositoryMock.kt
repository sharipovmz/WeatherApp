package com.example.weatherapp.data.repository

import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.delay

class FakeCityRepositoryMock : CityRepository {

    override suspend fun searchCities(query: String): List<City> {
        delay(500)
        return listOf(
            City("$query City", "US"),
            City("$query Town", "US")
        )
    }

    override suspend fun loadCityWeather(city: String): Result<Unit> {
        delay(500)
        return Result.success(Unit)
    }

    override fun getPopularCities(): List<City> = listOf(
        City("Mock City 1", "US"),
        City("Mock City 2", "US")
    )

    override fun getSearchHistory(): List<City> = listOf(
        City("Mock History 1", "US")
    )
}
