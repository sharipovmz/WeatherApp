# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

Android project using Gradle 8.13, AGP 8.13.2, Kotlin 2.0.21. On Windows, use `gradlew.bat` instead of `./gradlew`.

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

## Architecture

**Single-module Android app** using Clean Architecture (MVVM) with Jetpack Compose and Material 3.

- **Navigation:** AndroidX Navigation 3 (`androidx.navigation3`) with manual backstack (`MutableStateList<Any>`) and `NavDisplay` with state/ViewModel preservation decorators
- **State Management:** `MutableStateFlow<T>` in ViewModels, observed via `collectAsStateWithLifecycle()`
- **DI:** Service Locator pattern via `AppModule` object (no Hilt/Koin) — lazy singletons for repository and use cases, factory methods for ViewModels
- **SDK targets:** minSdk 24, compileSdk/targetSdk 36

### Package Structure

All source code is in `app/src/main/java/com/example/weatherapp/`:

```
├── MainActivity.kt              — Entry point, requests location permissions, launches NavExample()
├── data/repository/             — Fake repository implementations (FakeCityRepositoryBack, FakeCityRepositoryMock)
├── di/AppModule.kt              — Service locator: wires repository → use cases → ViewModels
├── domain/model/City.kt         — Domain model
├── domain/repository/           — CityRepository interface
├── domain/usecase/              — LoadCityWeatherUseCase, SearchCitiesUseCase
├── presentation/navigation/     — MainNavigation.kt: NavDisplay with two routes (MainWeatherScreen, CitySearchScreen)
├── presentation/weather/        — WeatherViewModel, WeatherState, WeatherAppScreen composable
├── presentation/search/         — SearchViewModel, SearchUiState, SearchIntent, SearchScreen composable
├── temp/                        — Unused message interface stubs (experimental)
└── ui/theme/                    — Material 3 theme, colors, typography
```

### Data Flow

1. User interaction triggers navigation or ViewModel method
2. ViewModel calls use case → repository (fake, with simulated delays)
3. `StateFlow.update { ... }` emits new state
4. Compose UI recomposes via `collectAsStateWithLifecycle()`

### Key Design Decisions

- **Shared WeatherViewModel:** Created once in `MainNavigation.kt` via `AppModule` and passed to both screens
- **SearchViewModel:** Uses intent-based pattern (`SearchIntent` sealed class) for user actions
- **Navigation 3:** Uses `rememberSaveableStateHolderNavEntryDecorator()` and `rememberViewModelStoreNavEntryDecorator()` for state preservation — this is a newer API, not the traditional NavHost pattern
- **Kotlin Serialization:** Plugin enabled, used for route serialization (`@Serializable` data objects for routes)

### Current State

Prototype with all weather data mocked in fake repositories. No network layer (Retrofit), database (Room), or real DI framework. Two screens: weather display and city search.
