package com.jk.mogunavi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "ホーム", Icons.Filled.Home)
    object Search : BottomNavItem("search", "検索", Icons.Filled.Search)
    object Detail : BottomNavItem("detail", "詳細", Icons.Filled.Info)
}
