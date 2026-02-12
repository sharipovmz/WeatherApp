package com.example.weatherapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

data object MainWeatherScreen
data object CitySearchScreen

@Composable
fun NavExample() {
    val backstack = remember { mutableStateListOf<Any>(MainWeatherScreen) }
    val viewModel: WeatherViewModel = viewModel()
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
                    SearchScreen(
                        onBack = { backstack.removeLastOrNull() },
                        onCitySelected = { city ->
                            viewModel.setCity(city)
                            backstack.removeLastOrNull()
                        }
                    )
                }
                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}
