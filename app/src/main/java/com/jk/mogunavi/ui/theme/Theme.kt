package com.jk.mogunavi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MoguColorScheme = lightColorScheme(
    primary = Cream,
    secondary = LightCream,
    background = Cream,
    surface = Cream,
    onPrimary = Brown,
    onSecondary = Brown,
    onBackground = Brown,
    onSurface = Brown
)

@Composable
fun MoguNaviTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MoguColorScheme,
        typography = Typography,
        content = content
    )
}
