package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jk.mogunavi.viewmodel.GourmetViewModel
import kotlin.math.*

@Composable
fun SearchScreen() {
    val viewModel: GourmetViewModel = viewModel()
    var query by remember { mutableStateOf("") }

    val distanceOptions = listOf("500m", "1km", "2km", "3km")
    val rangeMap = mapOf("500m" to 2, "1km" to 3, "2km" to 4, "3km" to 5)
    val minMap = mapOf("500m" to 0, "1km" to 500, "2km" to 1000, "3km" to 2000)
    val maxMap = mapOf("500m" to 500, "1km" to 1000, "2km" to 2000, "3km" to 3000)
    var selectedDistance by remember { mutableStateOf("1km") }

    val shopList by viewModel.shops.collectAsState()

    // 예시 위치 (우메다)
    val myLat = 34.668698
    val myLng = 135.501869

    val filteredShopList = remember(shopList, selectedDistance) {
        shopList.filter { shop ->
            val lat = shop.lat
            val lng = shop.lng
            if (lat != null && lng != null) {
                val distance = calculateDistance(myLat, myLng, lat, lng)
                distance in ((minMap[selectedDistance] ?: 0).toDouble()..(maxMap[selectedDistance] ?: 1000).toDouble())
            } else {
                false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.fetchShops(
                    apiKey = "d0240fe16771e4bd",
                    lat = myLat,
                    lng = myLng,
                    range = rangeMap[selectedDistance] ?: 3,
                    keyword = query
                )
            },
            placeholder = { Text("検索") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "검색 아이콘",
                    tint = Color(0xFFA47148)
                )
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFFFF8E6),
                focusedContainerColor = Color(0xFFFFF8E6),
                disabledContainerColor = Color(0xFFFFF8E6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFA47148),
                    shape = RoundedCornerShape(50)
                ),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            distanceOptions.forEach { label ->
                OutlinedButton(
                    onClick = {
                        selectedDistance = label
                        viewModel.fetchShops(
                            apiKey = "d0240fe16771e4bd",
                            lat = myLat,
                            lng = myLng,
                            range = rangeMap[label] ?: 3,
                            keyword = query
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedDistance == label) Color(0xFFA47148) else Color.White,
                        contentColor = if (selectedDistance == label) Color.White else Color(0xFFA47148)
                    ),
                    contentPadding = PaddingValues(vertical = 6.dp, horizontal = 0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = if (label == "500m") 12.sp else 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "검색어: $query, 거리: $selectedDistance",
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredShopList) { shop ->
                val distance = calculateDistance(myLat, myLng, shop.lat, shop.lng)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = shop.photo?.mobile?.l,
                            contentDescription = "${shop.name} 사진",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = shop.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "현재 위치로부터 약 ${distance.toInt()}m",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
    val R = 6371000.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLng = Math.toRadians(lng2 - lng1)
    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLng / 2).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}
