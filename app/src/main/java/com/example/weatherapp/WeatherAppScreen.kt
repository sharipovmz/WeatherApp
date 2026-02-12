package com.example.weatherapp

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherAppScreen(
    weather: WeatherState,

    onLocationClick: () -> Unit
)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xff06aae6),
                        Color(0xFF98DBF1)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        if (weather.progressBar) {
            LoadingExamples()
        }
        else {
        EarthIconLayout()
        SearchBarLayout(weather.city, onLocationClick)
        TopWeatherInfo(weather.temperature, weather.weather, weather.high, weather.low)
        HourlyWeatherInfo(weather.hForecast)
        WeatherAdditionalInfo()
        WeaklyForecast()
        }
    }
}

@Composable
fun LoadingExamples() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ColumnScope.EarthIconLayout() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(36.dp)
            .align(Alignment.End)
            .background(Color(0x9ACAE1E7), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.earth_svgrepo_com),
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
private fun SearchBarLayout(selectedCity: String, onLocationClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0x9ACAE1E7), shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp)
                .clickable { onLocationClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.ic_search),
                tint = Color.White,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp), text = selectedCity, color = Color.White
            )
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.location_svgrepo_com),
                tint = Color.White,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.location_svgrepo_com),
                tint = Color.White,
                contentDescription = null
            )
            Text(
                text = selectedCity, color = Color.White
            )
        }
    }
}


@Composable
private fun TopWeatherInfo(temperature: Int, weather: String, high: String, low: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(140.dp),
            painter = painterResource(R.drawable.cloud),
            tint = Color.White,
            contentDescription = null
        )
        Text(
            text = "$temperature°",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = weather,
            color = Color.White,
            fontSize = 25.sp
        )
        Row {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = high,
                color = Color.White
            )
            Text(text = low, color = Color.White)
        }
    }
}

@Composable
private fun HourlyWeatherInfo(hourly: List<HourlyWeather>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color(0x4FFFFFFF), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(3.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
            )

            Text(
                "Hourly Forecast",
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White,
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier.padding(top = 10.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {
                items(hourly, key = { it.hour }) { item ->
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = item.hour, fontSize = 16.sp, color = Color.White)
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.cloud),
                            contentDescription = null,
                            tint = Color.White,
                        )
                        Text(text = "${item.temp}°", fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherAdditionalInfo() {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(Color(0x4FFFFFFF), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp)
            .padding(vertical = 20.dp)
    ) {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherInfoItems(R.drawable.ic_wind, "Wind Speed", "12mph")
                WeatherInfoItems(R.drawable.ic_drop, "Humidity", "65%")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherInfoItems(R.drawable.ic_eye, "Visibility", "10 mi")
                WeatherInfoItems(R.drawable.ic_press, "Pressure", "1013 mb")
            }
        }
    }
}

@Composable
fun WeatherInfoItems(icon: Int, headTitle: String, downInfo: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(36.dp)
                .background(Color(0x9ACAE1E7), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon),
                tint = Color.White,
                contentDescription = null
            )
        }
        Column {
            Text(text = headTitle, fontSize = 16.sp, color = Color.Gray)
            Text(text = downInfo, fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
private fun WeaklyForecast() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth()
            .background(Color(0x4FFFFFFF), shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        DayItemLayout("Monday", "L:52В° H:62В°")
        DayItemLayout("Tuesday", "L:52В° H:62В°")
        DayItemLayout("Wednesday", "L:52В° H:62В°")
        DayItemLayout("Thursday", "L:52В° H:62В°")
        DayItemLayout("Friday", "L:52В° H:62В°")
        DayItemLayout("Saturday", "L:52В° H:62В°")
        DayItemLayout("Sunday", "L:52В° H:62В°")
    }
}

@Composable
private fun DayItemLayout(day: String, weather: String) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(day, color = Color.White, fontSize = 16.sp)

            Text(weather, color = Color.White, fontSize = 16.sp)
        }
        Icon(
            painter = painterResource(R.drawable.cloud),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center)
        )
    }
}



