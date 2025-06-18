package com.jk.mogunavi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jk.mogunavi.R

// Rounded Mplus 1c Bold 폰트 패밀리 정의
val RoundedMplus = FontFamily(
    Font(R.font.roundedmplus1c_bold, FontWeight.Bold)
)

// 앱 전체에 사용할 Typography 설정
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RoundedMplus,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
