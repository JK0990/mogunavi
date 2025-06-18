package com.jk.mogunavi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jk.mogunavi.ui.theme.MoguNaviTheme
import com.jk.mogunavi.ui.screen.HomeScreen //  HomeScreen 불러오기

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoguNaviTheme {
                HomeScreen() //  여기서 HomeScreen 호출
            }
        }
    }
}
