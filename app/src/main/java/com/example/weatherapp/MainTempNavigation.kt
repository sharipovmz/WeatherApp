package com.example.weatherapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

data object MainWeatherScreen
data object CitySearchScreen

@Composable
fun NavExample() {
    val backstack = remember { mutableStateListOf<Any>(MainWeatherScreen) }
    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is MainWeatherScreen -> NavEntry(key) {
                    WeatherAppScreen(
                        selectedCity = "San Francisco",
                        onLocationClick = {
                            println("ClickTrue")
                            backstack.add(CitySearchScreen) }
                    )
                }
                is CitySearchScreen -> NavEntry(key){
                    SearchScreen()
                }
                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}