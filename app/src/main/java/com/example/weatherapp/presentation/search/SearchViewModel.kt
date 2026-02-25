package com.example.weatherapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.usecase.SearchCitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCitiesUseCase: SearchCitiesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.SearchCity -> searchCity(intent.query)
            is SearchIntent.SelectCity -> selectCity(intent.city)
            is SearchIntent.ClearHistory -> clearHistory()
        }
    }

    private fun searchCity(query: String) {
        _state.update { it.copy(query = query) }
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                val results = searchCitiesUseCase(query)
                _state.update { it.copy(searchResults = results, isLoading = false) }
            }
        }
    }

    private fun selectCity(city: String) {
        _state.update { it.copy(query = city) }
    }

    private fun clearHistory() {
        _state.update { it.copy(searchHistory = emptyList()) }
    }
}
