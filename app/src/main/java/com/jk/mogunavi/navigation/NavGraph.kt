package com.jk.mogunavi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jk.mogunavi.ui.screen.HomeScreen
import com.jk.mogunavi.ui.screen.SplashScreen
// 필요 시 다른 스크린 import

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash" // ✅ 처음 시작은 스플래쉬
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        // 필요한 화면 추가 가능
    }
}
