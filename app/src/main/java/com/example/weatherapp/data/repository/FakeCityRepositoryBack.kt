package com.example.weatherapp.data.repository

import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.Coordinates
import com.example.weatherapp.domain.repository.CityRepository
import kotlinx.coroutines.delay

class FakeCityRepositoryBack : CityRepository {
    private val allCities = listOf(
        City("San Francisco, CA", "US"),
        City("New York, NY", "US"),
        City("Los Angeles, CA", "US"),
        City("Chicago, IL", "US"),
        City("Miami, FL", "US"),
        City("Seattle, WA", "US"),
        City("Austin, TX", "US")
    )

    private val history = mutableListOf(
        City("Los Angeles, CA", "US"),
        City("New York, NY", "US")
    )

    override suspend fun searchCities(query: String): List<City> {
        delay(1000)
        return allCities.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    override suspend fun loadCityWeather(city: String): Result<Unit> {
        delay(1000)
        return Result.success(Unit)
    }

    override suspend fun loadCityCoordinates(city: String): Result<Coordinates> {
        delay(500)
        return Result.success(Coordinates(lat = 34.0522, lon = -118.2437))
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CURRENT WEATHER — запрос и ответ (OpenWeatherMap API 2.5)
    //
    // GET https://api.openweathermap.org/data/2.5/weather
    //
    // Параметры запроса:
    //   q       = "Los Angeles"   // название города (String) — то что вводит пользователь
    //   units   = "metric"        // градусы Цельсия; "imperial" = Fahrenheit; без него — Кельвин
    //   lang    = "en"            // язык описания погоды (weather.description)
    //   appid   = "YOUR_API_KEY"  // твой ключ с openweathermap.org
    //
    // Пример полного URL:
    //   https://api.openweathermap.org/data/2.5/weather?q=Los+Angeles&units=metric&lang=en&appid=YOUR_API_KEY
    //
    // ─── Реальный JSON-ответ (Los Angeles) ───────────────────────────────────
    // {
    //   "coord": { "lon": -118.2437, "lat": 34.0522 },  // координаты (не нужны для UI)
    //   "weather": [
    //     {
    //       "id":          800,                // код условия погоды
    //       "main":        "Clear",            // → WeatherState.weather (короткое)
    //       "description": "clear sky",        // → WeatherState.weather (подробное)
    //       "icon":        "01n"               // → иконка: https://openweathermap.org/img/wn/01n@2x.png
    //     }                                    //   "n" = ночь, "d" = день
    //   ],
    //   "main": {
    //     "temp":       19.89,                 // → WeatherState.temperature → .toInt() = 19
    //     "feels_like": 18.38,                 // → "Feels like 18°"
    //     "temp_min":   17.52,                 // → WeatherState.low  ("L: 17°")
    //     "temp_max":   21.12,                 // → WeatherState.high ("H: 21°")
    //     "pressure":   1016,                  // → WeatherAdditionalInfo "Pressure" → "1016 hPa"
    //     "humidity":   17,                    // → WeatherAdditionalInfo "Humidity" → "17%"
    //     "sea_level":  1016,                  // давление на уровне моря (необязательно)
    //     "grnd_level": 995                    // давление у земли (необязательно)
    //   },
    //   "visibility": 10000,                   // → WeatherAdditionalInfo "Visibility" → 10000/1000 = "10 km"
    //   "wind": {
    //     "speed": 3.09,                       // → WeatherAdditionalInfo "Wind Speed" → "3 m/s"
    //     "deg":   10                          // направление ветра в градусах (необязательно)
    //   },
    //   "clouds": { "all": 0 },               // облачность 0% (ясно)
    //   "dt": 1772949267,                      // Unix timestamp → для отображения времени
    //   "sys": {
    //     "country": "US",                     // → WeatherState.city = "Los Angeles, US"
    //     "sunrise": 1772892847,               // → время восхода (Unix → HH:mm)
    //     "sunset":  1772934845                // → время заката  (Unix → HH:mm)
    //   },
    //   "timezone": -28800,                    // UTC offset в секундах (UTC-8 = LA)
    //   "name": "Los Angeles",                 // → WeatherState.city
    //   "cod": 200                             // 200 = успех, 404 = город не найден, 401 = неверный ключ
    // }
    //
    // ВАЖНО: поле hForecast (почасовой прогноз) из этого запроса не получить.
    // Для этого нужен отдельный запрос — см. блок FORECAST ниже.
    // ─────────────────────────────────────────────────────────────────────────

    // ─────────────────────────────────────────────────────────────────────────
    // HOURLY + WEEKLY FORECAST — один запрос, два применения
    //
    // Бесплатный план даёт: 5 дней, шаг 3 часа (~40 точек итого)
    // 7-дневный прогноз — только в платном OneCall API 3.0
    //
    // GET https://api.openweathermap.org/data/2.5/forecast
    //
    // Параметры запроса:
    //   q       = "Los Angeles"   // название города
    //   units   = "metric"        // °C
    //   lang    = "en"            // язык описания
    //   cnt     = 40              // кол-во точек (макс 40 = 5 дней); убери чтобы получить все
    //   appid   = "YOUR_API_KEY"
    //
    // Пример URL:
    //   https://api.openweathermap.org/data/2.5/forecast?q=Los+Angeles&units=metric&lang=en&appid=YOUR_API_KEY
    //
    // ─── JSON-ответ ───────────────────────────────────────────────────────────
    // {
    //   "city": {
    //     "name":    "Los Angeles",            // название города
    //     "country": "US"
    //   },
    //   "list": [                              // массив ~40 точек (каждые 3 часа)
    //     {
    //       "dt_txt":  "2024-03-08 15:00:00",  // дата и время точки
    //       "main": {
    //         "temp":      18.5,               // → HourlyWeather.temp / дневная температура
    //         "temp_min":  14.0,               // → минимум дня (для WeaklyForecast)
    //         "temp_max":  22.3,               // → максимум дня (для WeaklyForecast)
    //         "humidity":  65
    //       },
    //       "weather": [
    //         {
    //           "main":        "Rain",         // → краткое название
    //           "description": "light rain",   // → подробное описание
    //           "icon":        "10d"           // → HourlyWeather.icon
    //         }
    //       ],
    //       "wind": {
    //         "speed": 5.1                     // → скорость ветра
    //       },
    //       "pop": 0.32                        // → вероятность осадков (0.0–1.0, т.е. 32%)
    //     },
    //     // ... ещё ~39 таких объектов
    //   ]
    // }
    //
    // ─── Как использовать list[] ──────────────────────────────────────────────
    //
    // ПОЧАСОВОЙ (hForecast в WeatherState):
    //   Берёшь первые 8 элементов list[] → 8 × 3ч = 24 часа вперёд
    //   list[0] → "Now", list[1] → "+3ч", list[2] → "+6ч", ...
    //   dt_txt "2024-03-08 15:00:00" → показываешь "15:00"
    //   → HourlyWeather(hour = "15:00", icon = weather[0].icon, temp = main.temp.toInt().toString())
    //
    // НЕДЕЛЬНЫЙ (WeaklyForecast):
    //   Группируешь list[] по дате (первые 5 символов dt_txt = "2024-03-08")
    //   Из каждой группы берёшь temp_min (минимальный) и temp_max (максимальный)
    //   dt (Unix timestamp) → конвертируешь в день недели через java.util.Calendar или SimpleDateFormat
    //   → DayItemLayout(day = "Monday", weather = "L:14° H:22°")
    //
    // Итого: 2 запроса на весь экран — /weather (текущее) + /forecast (часы + дни)
    // ─────────────────────────────────────────────────────────────────────────

    override fun getPopularCities(): List<City> = allCities

    override fun getSearchHistory(): List<City> = history
}
