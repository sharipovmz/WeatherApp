package com.example.weatherapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

data object MainWeatherScreen
data object CitySearchScreen

@Composable
fun NavExample() {
    val backstack = remember { mutableStateListOf<Any>(MainWeatherScreen) }
    val currentCity = remember { mutableStateOf("Los Angeles, CA") }
    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is MainWeatherScreen -> NavEntry(key) {
                    WeatherAppScreen(
                        selectedCity = currentCity.value,
                        onLocationClick = {
                            println("ClickTrue")
                            backstack.add(CitySearchScreen) }
                    )
                }
                is CitySearchScreen -> NavEntry(key){
                    SearchScreen(
                        onBack = { backstack.removeLastOrNull() },
                        onCitySelected = { city ->
                            currentCity.value = city
                            backstack.removeLastOrNull()
                        }
                    )
                }
                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}

