package com.jk.mogunavi.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.jk.mogunavi.R
import com.jk.mogunavi.viewmodel.GourmetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GourmetViewModel = viewModel()
) {
    val shops by viewModel.shops.collectAsState()
    val currentAddress by viewModel.currentAddress.collectAsState()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var showModal by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 10000
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val location = result.lastLocation
                    if (location != null) {
                        viewModel.fetchShops("d0240fe16771e4bd", location.latitude, location.longitude)
                        viewModel.fetchCurrentAddressFromLatLng(context, location.latitude, location.longitude)
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showModal = true }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = "位置アイコン",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = currentAddress ?: "位置情報を取得中…",
                color = Color(0xFFA47148),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .shadow(
                    elevation = 18.dp,
                    shape = RectangleShape,
                    clip = false
                )
                .background(MaterialTheme.colorScheme.primary, shape = RectangleShape)
                .clip(RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mogunavi_logo),
                contentDescription = "マスコット",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "本日のおすすめ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.roundedmplus1c_bold, weight = FontWeight.Bold)
                ),
                color = Color(0xFFA47148)
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (shops.isNotEmpty()) {
            val visibleShops = remember(shops) {
                shops.shuffled().take(5)
            }

            HorizontalPager(
                count = visibleShops.size,
                state = pagerState,
                contentPadding = PaddingValues(start = 8.dp, end = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
            ) { page ->
                val shop = visibleShops[page]

                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(300.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        AsyncImage(
                            model = shop.photo.mobile.l,
                            contentDescription = shop.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = "${shop.name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(
                                    Font(R.font.roundedmplus1c_bold, weight = FontWeight.Bold)
                                ),
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "アクセス: ${shop.access ?: "情報なし"}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(
                                    Font(R.font.roundedmplus1c_bold, weight = FontWeight.Normal)
                                ),
                                color = Color.DarkGray,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        } else {
            Text(
                text = "おすすめ店舗を読み込み中...",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if (showModal) {
        LocationModal(
            onDismiss = { showModal = false }
        )
    }
}
