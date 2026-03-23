package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.Coordinates
import com.example.weatherapp.domain.repository.CityRepository

class LoadCityCoordinatesUseCase(private val repository: CityRepository) {
    suspend operator fun invoke(city: String): Result<Coordinates> {
        return repository.loadCityCoordinates(city)
    }
}
