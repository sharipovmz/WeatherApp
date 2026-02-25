# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

This is an Android project using Gradle 8.13 with AGP 8.13.2.

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run a single test class
./gradlew testDebugUnitTest --tests "com.example.weatherapp.ExampleUnitTest"

# Run Android instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Architecture

**Single-module Android app** using MVVM with Jetpack Compose and Kotlin 2.0.21.

- **UI Framework:** Jetpack Compose with Material 3
- **Navigation:** AndroidX Navigation 3 (`androidx.navigation3`) with manual backstack management
- **State Management:** `StateFlow` in ViewModel, observed via `collectAsStateWithLifecycle()`
- **SDK targets:** minSdk 24, compileSdk/targetSdk 36

### Key Files

All source code is in `com.example.weatherapp`:

- `MainActivity.kt` — Entry point, launches `NavExample()` composable
- `MainTempNavigation.kt` — Navigation setup with two routes: `MainWeatherScreen` and `CitySearchScreen`. Uses `NavDisplay` with state/ViewModel preservation decorators. The `WeatherViewModel` is shared across both screens.
- `WeatherViewModel.kt` — Central state holder using `MutableStateFlow<WeatherState>`. Currently uses simulated loading (2s delay coroutine) with no real API integration.
- `WeatherState.kt` — Data classes: `WeatherState` (city, temperature, weather, forecasts, loading flag) and `HourlyWeather`
- `WeatherAppScreen.kt` — Main weather display (temperature, hourly forecast, weekly forecast, weather details grid)
- `SearchScreen.kt` — City search with hardcoded history and popular cities list

### Data Flow

User interaction → Navigation change / `ViewModel.setCity()` → `ViewModel.isLoad()` sets loading state → `StateFlow.update()` → UI recomposes via `collectAsStateWithLifecycle()`

### Current State

This is a prototype with all weather data hardcoded/mocked. There is no network layer, database, or dependency injection. The app has two screens: weather display and city search.
