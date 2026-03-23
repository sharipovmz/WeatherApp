# Repository Guidelines

## Project Structure & Module Organization
This repository is a single-module Android app in [`app/`](C:\Mahmudjon\ITRUN\WeatherApp\app). Kotlin sources live in `app/src/main/java/com/example/weatherapp` and are split by responsibility: `data/`, `domain/`, `presentation/`, `di/`, and `ui/theme/`. Compose entry points start at `MainActivity.kt` and navigation lives under `presentation/navigation/`. Resources and drawables are in `app/src/main/res`. Local JVM tests belong in `app/src/test`, and device or emulator tests belong in `app/src/androidTest`.

## Build, Test, and Development Commands
Use Gradle from the repo root. Android Gradle Plugin `8.13.2` requires JDK 17; Gradle commands will fail under Java 11.

- `.\gradlew.bat assembleDebug` builds the debug APK.
- `.\gradlew.bat installDebug` installs the debug build on a connected device or emulator.
- `.\gradlew.bat testDebugUnitTest` runs local JVM unit tests in `app/src/test`.
- `.\gradlew.bat connectedDebugAndroidTest` runs instrumented tests on a device or emulator.
- `.\gradlew.bat lint` runs Android lint checks for Kotlin, Compose, and resources.

## Coding Style & Naming Conventions
Follow Kotlin official style (`kotlin.code.style=official`) with 4-space indentation and trailing commas where Android Studio suggests them. Use `PascalCase` for composables, activities, view models, and data classes; use `camelCase` for methods and state fields; keep package names lowercase. Name screens by feature, such as `SearchScreen.kt` and `WeatherViewModel.kt`. Keep experimental or throwaway integrations isolated under `temp/` until they are promoted.

## Testing Guidelines
Name test files with the subject plus `Test`, for example `SearchCitiesUseCaseTest.kt`. Prefer fast JVM tests for domain and view-model logic, and reserve `androidTest` for UI, navigation, and Android framework behavior. Add or update tests with each behavior change; if UI output changes, include at least one manual verification note in the PR.

## Commit & Pull Request Guidelines
Recent history uses short, change-focused commit titles such as `creating api structure` and `turning whole project into MVVM`. Keep commits small, scoped to one concern, and written as a concise imperative summary. PRs should include a brief description, testing performed, related issue or task link, and screenshots or recordings for visible Compose UI changes.

## Configuration Tips
Do not commit `local.properties`, `.idea/`, build outputs, or JDK archives; they are already ignored. Keep secrets and machine-specific SDK paths out of source control.
