package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.remote.dto.CurrentWeatherResponseDto
import com.example.weatherapp.data.remote.dto.ForecastItemDto
import com.example.weatherapp.data.remote.dto.ForecastResponseDto
import com.example.weatherapp.domain.model.CurrentWeather
import com.example.weatherapp.domain.model.DailyForecast
import com.example.weatherapp.domain.model.HourlyForecast
import com.example.weatherapp.domain.model.Weather

private const val HOURLY_COUNT = 12
private const val DAILY_COUNT = 7
private const val SECONDS_PER_DAY = 86400

/**
 * Собирает доменную модель из ответов бесплатных API (Current Weather + 5 Day Forecast).
 */
fun toWeather(
    current: CurrentWeatherResponseDto,
    forecast: ForecastResponseDto
): Weather? {
    val currentWeather = current.toDomain() ?: return null
    val hourly = forecast.list.take(HOURLY_COUNT).mapNotNull { it.toHourlyDomain() }
    val daily = forecast.list.toDailyDomain().take(DAILY_COUNT)
    return Weather(
        current = currentWeather,
        hourly = hourly,
        daily = daily
    )
}

fun CurrentWeatherResponseDto.toDomain(): CurrentWeather? {
    val main = main ?: return null
    val weatherFirst = weather.firstOrNull() ?: return null
    val windSpeed = wind?.speed ?: 0.0
    return CurrentWeather(
        temperature = main.temp,
        feelsLike = main.feelsLike,
        humidity = main.humidity,
        windSpeed = windSpeed,
        description = weatherFirst.description.ifEmpty { weatherFirst.main },
        icon = weatherFirst.icon
    )
}

fun ForecastItemDto.toHourlyDomain(): HourlyForecast? {
    val m = main ?: return null
    val weatherFirst = weather.firstOrNull() ?: return null
    return HourlyForecast(
        dt = dt,
        temp = m.temp,
        feelsLike = m.feelsLike,
        description = weatherFirst.description.ifEmpty { weatherFirst.main },
        icon = weatherFirst.icon
    )
}

/**
 * Группирует 3-часовые срезы по дням, считает min/max за день и возвращает дневной прогноз.
 */
fun List<ForecastItemDto>.toDailyDomain(): List<DailyForecast> {
    if (isEmpty()) return emptyList()
    val byDay = groupBy { it.dt / SECONDS_PER_DAY }
    return byDay.values.mapNotNull { dayItems ->
        val first = dayItems.minByOrNull { it.dt } ?: return@mapNotNull null
        val main = first.main ?: return@mapNotNull null
        val weatherFirst = first.weather.firstOrNull() ?: return@mapNotNull null
        val temps = dayItems.mapNotNull { it.main?.temp }
        if (temps.isEmpty()) return@mapNotNull null
        DailyForecast(
            dt = first.dt,
            tempMin = temps.min(),
            tempMax = temps.max(),
            description = weatherFirst.description.ifEmpty { weatherFirst.main },
            icon = weatherFirst.icon
        )
    }.sortedBy { it.dt }
}
