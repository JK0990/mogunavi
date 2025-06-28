package com.jk.mogunavi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jk.mogunavi.R
import com.jk.mogunavi.data.remote.model.Shop

@Composable
fun ShopDetailModal(
    shop: Shop,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xAA000000))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFFFF8E6))
                .padding(20.dp)
        ) {
            AsyncImage(
                model = shop.photo.mobile.l,
                contentDescription = "店舗の画像",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${shop.name}",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.roundedmplus1c_bold))
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "住所: ${shop.address}",
                fontSize = 16.sp,
                color = Color.DarkGray,
                fontFamily = FontFamily(Font(R.font.roundedmplus1c_bold))
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "営業時間: ${shop.open ?: "情報なし"}",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.roundedmplus1c_bold))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("閉じる")
            }
        }
    }
}
