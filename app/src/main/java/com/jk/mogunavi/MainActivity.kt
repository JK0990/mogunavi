package com.jk.mogunavi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jk.mogunavi.ui.theme.MoguNaviTheme
import com.jk.mogunavi.navigation.MoguNaviApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoguNaviTheme {
                MoguNaviApp()
            }
        }
    }
}

