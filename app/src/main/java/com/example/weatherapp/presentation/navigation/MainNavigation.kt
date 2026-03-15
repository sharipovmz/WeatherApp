package com.example.weatherapp.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.weatherapp.di.AppModule
import com.example.weatherapp.presentation.search.SearchIntent
import com.example.weatherapp.presentation.search.SearchScreen
import com.example.weatherapp.presentation.search.SearchViewModel
import com.example.weatherapp.presentation.weather.WeatherAppScreen
import com.example.weatherapp.presentation.weather.WeatherViewModel

data object MainWeatherScreen
data object CitySearchScreen

@Composable
fun NavExample() {
    val backstack = remember { mutableStateListOf<Any>(MainWeatherScreen) }
    val viewModel: WeatherViewModel = remember { AppModule.provideWeatherViewModel() }
    val searchViewModel: SearchViewModel = remember { AppModule.provideSearchViewModel() }
    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when (key) {
                is MainWeatherScreen -> NavEntry(key) {
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    WeatherAppScreen(
                        weather = state,
                        onLocationClick = { backstack.add(CitySearchScreen) }
                    )
                }
                is CitySearchScreen -> NavEntry(key){
                    val weatherState by viewModel.state.collectAsStateWithLifecycle()
                    val searchState by searchViewModel.state.collectAsStateWithLifecycle()
                    val citySelected = remember { mutableStateOf(false) }

                    LaunchedEffect(weatherState.isLoading, citySelected.value) {
                        if (citySelected.value && !weatherState.isLoading) {
                            backstack.removeLastOrNull()
                            viewModel.isLoad()
                        }
                    }

                    SearchScreen(
                        uiState = searchState,
                        weatherIsLoading = weatherState.isLoading,
                        onBack = { backstack.removeLastOrNull() },
                        onQueryChange = { query ->
                            searchViewModel.handleIntent(SearchIntent.SearchCity(query))
                        },
                        onCitySelected = { city ->
                            viewModel.setCity(city)
                            citySelected.value = true
                        }
                    )
                }
                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}
