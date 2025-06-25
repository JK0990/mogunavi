package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jk.mogunavi.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // 2초 대기 후 홈으로 이동
    LaunchedEffect(Unit) {
        delay(2000L)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBEEC1)), // 크림색 배경
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mogunavi_logo),
                contentDescription = "앱 로고",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RectangleShape),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "モグナビ",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFA47148)
            )
        }
    }
}
