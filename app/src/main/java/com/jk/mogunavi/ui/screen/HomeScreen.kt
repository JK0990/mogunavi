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
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val apiKey = "d0240fe16771e4bd"
    val lat = 34.7055
    val lng = 135.4983

    var showModal by remember { mutableStateOf(false) }

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
        // 현재 위치 표시 (지도 아이콘 + 주소 텍스트)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showModal = true } // ✅ 모달 열기 트리거
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = "위치 아이콘",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "성안청구타운",
                color = Color(0xFFA47148),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔍 SearchBar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, shape = RectangleShape)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = "가게 검색", color = Color.Gray, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🐻 마스코트 이미지
        Image(
            painter = painterResource(id = R.drawable.mogunavi_logo),
            contentDescription = "마스코트",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 추천 섹션 제목
        Text(
            text = "本日のおすすめ",
            color = Color(0xFFA47148),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 추천 가게 스와이프
        if (shops.isNotEmpty()) {
            val visibleShops = shops.shuffled().take(5)

            HorizontalPager(
                count = visibleShops.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) { page ->
                val shop = visibleShops[page]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = shop.photo.mobile.l,
                        contentDescription = shop.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = shop.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Text(
                        text = shop.open ?: "영업시간 정보 없음",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
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

    // ✅ 위치 설정 모달
    if (showModal) {
        LocationModal(
            onDismiss = { showModal = false }
        )
    }
}
