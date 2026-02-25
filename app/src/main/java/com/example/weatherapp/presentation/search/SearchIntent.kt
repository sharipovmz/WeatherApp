package com.example.weatherapp.presentation.search

sealed class SearchIntent {
    data class SearchCity(val query: String) : SearchIntent()
    data class SelectCity(val city: String) : SearchIntent()
    data object ClearHistory : SearchIntent()
}
