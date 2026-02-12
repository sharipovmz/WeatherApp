package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun SearchScreenApp() {
    SearchScreen(onBack = {}, onCitySelected = {})
}

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onCitySelected: (String) -> Unit
) {
    val queryState = remember { mutableStateOf("") }
    val onCityClick = { city: String ->
        queryState.value = city
        onCitySelected(city)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF55A7F5),
                        Color(0xFFB9DDFF)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        SearchHeader(
            query = queryState.value,
            onQueryChange = { queryState.value = it },
            onBack = onBack
        )
        Spacer(modifier = Modifier.height(18.dp))
        SearchHistoryCard(onCityClick = onCityClick)
        Spacer(modifier = Modifier.height(16.dp))
        PopularCitiesCard(onCityClick = onCityClick)
    }
}

@Composable
private fun SearchHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(Color(0x6AD6E8F6), shape = CircleShape)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(R.drawable.ic_backarrow),
                tint = Color.White,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        TextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            placeholder = { Text(text = "Поиск города...", fontSize = 14.sp) },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .shadow(6.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color(0xFF1F2A36),
                unfocusedTextColor = Color(0xFF1F2A36),
                focusedPlaceholderColor = Color(0xFF8B9BB0),
                unfocusedPlaceholderColor = Color(0xFF8B9BB0),
                cursorColor = Color(0xFF4D8DF7)
            )
        )
    }
}

@Composable
private fun SearchHistoryCard(onCityClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.ic_clock),
                    tint = Color(0xFF7D8FA6),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "История поиска",
                    color = Color(0xFF5B6B7C),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Очистить",
                    color = Color(0xFF4D8DF7),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            SearchListItem(
                title = "Los Angeles, CA",
                highlight = false,
                showRemove = false,
                onClick = { onCityClick("Los Angeles, CA") }
            )
            SearchListItem(
                title = "New York, NY",
                highlight = true,
                showRemove = true,
                onClick = { onCityClick("New York, NY") }
            )
        }
    }
}

@Composable
private fun PopularCitiesCard(onCityClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Популярные города",
                color = Color(0xFF5B6B7C),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            SearchListItem(title = "San Francisco, CA", onClick = { onCityClick("San Francisco, CA") })
            SearchListItem(title = "New York, NY", onClick = { onCityClick("New York, NY") })
            SearchListItem(title = "Los Angeles, CA", highlight = true, onClick = { onCityClick("Los Angeles, CA") })
            SearchListItem(title = "Chicago, IL", onClick = { onCityClick("Chicago, IL") })
            SearchListItem(title = "Miami, FL", onClick = { onCityClick("Miami, FL") })
            SearchListItem(title = "Seattle, WA", onClick = { onCityClick("Seattle, WA") })
            SearchListItem(title = "Austin, TX", onClick = { onCityClick("Austin, TX") })
        }
    }
}

@Composable
private fun SearchListItem(
    title: String,
    highlight: Boolean = false,
    showRemove: Boolean = false,
    onClick: () -> Unit
) {
    val background = if (highlight) Color(0xFFE6F0FF) else Color.Transparent
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.location_svgrepo_com),
            tint = Color(0xFF4D8DF7),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            text = title,
            color = Color(0xFF2B3541),
            fontSize = 14.sp
        )
        if (showRemove) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.ic_close),
                tint = Color(0xFFE16969),
                contentDescription = null
            )
        }
    }
}