package com.jk.mogunavi.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.*
import com.jk.mogunavi.viewmodel.GourmetViewModel
import com.jk.mogunavi.ui.components.ShopItemCard
import com.jk.mogunavi.ui.components.ShopDetailModal
import com.jk.mogunavi.data.remote.model.Shop
import kotlinx.coroutines.launch
import kotlin.math.*

@Composable
fun SearchScreen() {
    val viewModel: GourmetViewModel = viewModel()
    val context = LocalContext.current

    var query by remember { mutableStateOf("") }
    var selectedDistance by remember { mutableStateOf("500m") }
    val currentPage = remember { mutableStateOf(0) }
    val itemsPerPage = 10

    val distanceOptions = listOf("500m", "1km", "2km", "3km")
    val rangeMap = mapOf("500m" to 2, "1km" to 3, "2km" to 4, "3km" to 5)
    val minMap = mapOf("500m" to 0, "1km" to 500, "2km" to 1000, "3km" to 2000)
    val maxMap = mapOf("500m" to 500, "1km" to 1000, "2km" to 2000, "3km" to 3000)

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shopList by viewModel.shops.collectAsState()
    val selectedShop = remember { mutableStateOf<Shop?>(null) }

    var myLat by remember { mutableStateOf(0.0) }
    var myLng by remember { mutableStateOf(0.0) }


    fun fetchAllPages(lat: Double, lng: Double) {
        viewModel.fetchShops(
            apiKey = "d0240fe16771e4bd",
            lat = lat,
            lng = lng,
            range = rangeMap[selectedDistance] ?: 3,
            keyword = query
        )
    }

    // 위치 가져오기 (1회)
    LaunchedEffect(Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    myLat = it.latitude
                    myLng = it.longitude
                    fetchAllPages(myLat, myLng)
                }
            }
        }
    }

    val filteredShopList = remember(shopList, selectedDistance, myLat, myLng) {
        currentPage.value = 0
        shopList.filter { shop ->
            val lat = shop.lat
            val lng = shop.lng
            if (lat != null && lng != null) {
                val distance = calculateDistance(myLat, myLng, lat, lng)
                distance in ((minMap[selectedDistance] ?: 0).toDouble()..(maxMap[selectedDistance] ?: 1000).toDouble())
            } else false
        }
    }

    val totalPages = (filteredShopList.size + itemsPerPage - 1) / itemsPerPage
    val pagedShopList = filteredShopList.drop(currentPage.value * itemsPerPage)
        .take(itemsPerPage)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(
            query = query,
            onQueryChanged = {
                query = it
                fetchAllPages(myLat, myLng)
            },
            onSearchConfirmed = {
                fetchAllPages(myLat, myLng)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            distanceOptions.forEach { label ->
                OutlinedButton(
                    onClick = {
                        selectedDistance = label
                        fetchAllPages(myLat, myLng)
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedDistance == label) Color(0xFFA47148) else Color.White,
                        contentColor = if (selectedDistance == label) Color.White else Color(0xFFA47148)
                    ),
                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .height(40.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            items(pagedShopList) { shop ->
                ShopItemCard(
                    shop = shop,
                    onClick = { selectedShop.value = shop }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (currentPage.value > 0) {
                        currentPage.value--
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                },
                enabled = currentPage.value > 0
            ) {
                Text("이전")
            }

            Text(text = "${currentPage.value + 1} / $totalPages")

            Button(
                onClick = {
                    if ((currentPage.value + 1) * itemsPerPage < filteredShopList.size) {
                        currentPage.value++
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                },
                enabled = (currentPage.value + 1) * itemsPerPage < filteredShopList.size
            ) {
                Text("다음")
            }
        }
    }

    if (selectedShop.value != null) {
        ShopDetailModal(
            shop = selectedShop.value!!,
            onDismiss = { selectedShop.value = null }
        )
    }
}

fun calculateDistance(lat1: Double, lng1: Double, lat2: Double?, lng2: Double?): Double {
    if (lat2 == null || lng2 == null) return Double.MAX_VALUE
    val R = 6371000.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLng = Math.toRadians(lng2 - lng1)
    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLng / 2).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}
