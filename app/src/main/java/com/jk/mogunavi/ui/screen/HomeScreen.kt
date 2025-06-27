package com.jk.mogunavi.ui.screen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
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

    val apiKey = "d0240fe16771e4bd"
    val lat = 34.7055
    val lng = 135.4983

    var showModal by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        viewModel.fetchShops(apiKey, lat, lng)
        viewModel.fetchCurrentAddressFromLatLng(context, lat, lng)
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
                contentDescription = "위치 아이콘",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = currentAddress ?: "위치를 불러오는 중...",
                color = Color(0xFFA47148),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SearchBar(
            query = searchQuery,
            onQueryChanged = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.mogunavi_logo),
            contentDescription = "마스코트",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "本日のおすすめ",
            color = Color(0xFFA47148),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (shops.isNotEmpty()) {
            val visibleShops = shops.shuffled().take(5)

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
                                text = shop.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "오시는 길: ${shop.access ?: "정보 없음"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        } else {
            Text(
                text = "추천 가게를 불러오는 중...",
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
