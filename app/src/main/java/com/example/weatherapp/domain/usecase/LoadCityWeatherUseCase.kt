package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.CityRepository

class LoadCityWeatherUseCase(private val repository: CityRepository) {
    suspend operator fun invoke(city: String): Result<Unit> {
        return repository.loadCityWeather(city)
    }
}
