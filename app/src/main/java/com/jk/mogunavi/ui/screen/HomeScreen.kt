package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jk.mogunavi.R
import com.jk.mogunavi.viewmodel.GourmetViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(viewModel: GourmetViewModel = viewModel()) {
    val shops by viewModel.shops.collectAsState()

    val apiKey = "d0240fe16771e4bd"
    val lat = 34.7055
    val lng = 135.4983

    // API 호출
    LaunchedEffect(true) {
        viewModel.fetchShops(apiKey, lat, lng)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로고 + 제목
        Image(
            painter = painterResource(id = R.drawable.mogunavi_logo),
            contentDescription = "로고",
            modifier = Modifier
                .size(120.dp)
                .clip(RectangleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "モグナビ",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 26.sp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 섹션 타이틀
        Text(
            text = "本日のおすすめ", // 오늘의 추천
            color = Color(0xFF6B4E2E), // 진한 브라운
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 가게를 랜덤으로 표시 (오늘의 추천)
        if (shops.isNotEmpty()) {
            val shop = shops.random()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White)
                    .padding(bottom = 8.dp)
            ) {
                AsyncImage(
                    model = shop.photo.mobile.l,
                    contentDescription = shop.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = shop.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(
                    text = shop.open ?: "영업시간 정보 없음",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        } else {
            Text(
                text = "추천 가게를 불러오는 중...",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
