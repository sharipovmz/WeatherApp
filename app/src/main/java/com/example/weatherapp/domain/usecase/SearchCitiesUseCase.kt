package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.repository.CityRepository

class SearchCitiesUseCase(private val repository: CityRepository) {
    suspend operator fun invoke(query: String): List<City> {
        return repository.searchCities(query)
    }
}
