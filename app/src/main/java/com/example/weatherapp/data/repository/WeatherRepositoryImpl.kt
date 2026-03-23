package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mapper.toWeather
import com.example.weatherapp.data.remote.OpenWeatherApi
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImpl(
    private val api: OpenWeatherApi,
    private val apiKey: String
) : IWeatherRepository {

    override suspend fun getWeatherByCoordinates(lat: Double, lon: Double): Result<Weather> = withContext(Dispatchers.IO) {
        try {
            val currentDeferred = api.getCurrentWeather(lat = lat, lon = lon, appid = apiKey)
            val forecastDeferred =  api.getForecast(lat = lat, lon = lon, appid = apiKey)
            val weather = toWeather(currentDeferred, forecastDeferred)
            if (weather == null) {
                Result.failure(Exception("Пустой или неверный ответ от сервера"))
            } else {
                Result.success(weather)
            }
        } catch (e: HttpException) {
            val message = when (e.code()) {
                401 -> "Неверный API ключ. Проверьте ключ на https://home.openweathermap.org/api_keys"
                404 -> "Данные для указанных координат не найдены"
                429 -> "Превышен лимит запросов. Попробуйте позже"
                else -> "Ошибка сервера: ${e.code()} — ${e.message()}"
            }
            Result.failure(Exception(message))
        } catch (e: IOException) {
            Result.failure(Exception("Нет соединения с интернетом: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка: ${e.message}"))
        }
    }
}
