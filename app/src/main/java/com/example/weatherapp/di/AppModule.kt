package com.example.weatherapp.di

import com.example.weatherapp.data.repository.FakeCityRepositoryBack
import com.example.weatherapp.domain.repository.CityRepository
import com.example.weatherapp.domain.usecase.LoadCityWeatherUseCase
import com.example.weatherapp.domain.usecase.SearchCitiesUseCase
import com.example.weatherapp.presentation.search.SearchViewModel
import com.example.weatherapp.presentation.weather.WeatherViewModel

object AppModule {

    private val repository: CityRepository by lazy { FakeCityRepositoryBack() }

    private val loadCityWeatherUseCase: LoadCityWeatherUseCase by lazy {
        LoadCityWeatherUseCase(repository)
    }

    private val searchCitiesUseCase: SearchCitiesUseCase by lazy {
        SearchCitiesUseCase(repository)
    }

    fun provideWeatherViewModel(): WeatherViewModel {
        return WeatherViewModel(loadCityWeatherUseCase)
    }

    fun provideSearchViewModel(): SearchViewModel {
        return SearchViewModel(searchCitiesUseCase)
    }
}
