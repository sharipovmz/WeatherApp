package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.CurrentWeatherResponseDto
import com.example.weatherapp.data.remote.dto.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApi {

    /** Current Weather API — текущая погода по координатам. */
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): CurrentWeatherResponseDto

    /** 5 Day / 3 Hour Forecast — прогноз на 5 дней (интервал 3 часа). */
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): ForecastResponseDto
}
