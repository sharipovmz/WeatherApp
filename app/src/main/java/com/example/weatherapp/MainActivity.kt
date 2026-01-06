package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppScreen()
}

@Composable
private fun WeatherAppScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xff06aae6),
                        Color(0xFF98DBF1)
                    )
                )
            )
    ) {
        EarthIconLayout()
        SearchCountryLayout()
    }
}

@Composable
private fun ColumnScope.EarthIconLayout() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(36.dp)
            .align(Alignment.End)
            .background(Color(0x5f738a70), shape = CircleShape),
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
private fun ColumnScope.SearchCountryLayout() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(40.dp)
            .background(Color(0xFF84A2A8)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_search),
            tint = Color.White,
            contentDescription = null)
        Text(text = "Los Angeles, CA", color = Color.White)
        Icon(modifier = Modifier
            .size(24.dp),
            painter = painterResource(R.drawable.location_svgrepo_com),
            tint = Color.White,
            contentDescription = null)
    }
}

//@Composable
//private fun Practice() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(1000.dp)
//            .background(Color.Red)
//    ) { }
//}