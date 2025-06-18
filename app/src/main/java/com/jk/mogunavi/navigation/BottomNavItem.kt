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
    object Home : BottomNavItem("home", "홈", Icons.Filled.Home)
    object Search : BottomNavItem("search", "검색", Icons.Filled.Search)
    object Detail : BottomNavItem("detail", "상세", Icons.Filled.Info)
}