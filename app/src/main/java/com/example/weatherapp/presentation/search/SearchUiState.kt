package com.example.weatherapp.presentation.search

import com.example.weatherapp.domain.model.City

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchHistory: List<City> = listOf(
        City("Los Angeles, CA", "US"),
        City("New York, NY", "US")
    ),
    val popularCities: List<City> = listOf(
        City("San Francisco, CA", "US"),
        City("New York, NY", "US"),
        City("Los Angeles, CA", "US"),
        City("Chicago, IL", "US"),
        City("Miami, FL", "US"),
        City("Seattle, WA", "US"),
        City("Austin, TX", "US")
    ),
    val searchResults: List<City> = emptyList()
)
