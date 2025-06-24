package com.jk.mogunavi.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jk.mogunavi.data.remote.model.Shop

@Composable
fun ShopItemCard(
    shop: Shop,
    onClick: () -> Unit // ✅ 클릭 이벤트 파라미터 추가
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // ✅ 클릭 시 이벤트 실행
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = shop.photo.mobile.l,
                contentDescription = "가게 이미지",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = shop.name, fontSize = 18.sp)
                Text(text = shop.address, fontSize = 14.sp)
                Text(text = "영업시간: ${shop.open ?: "정보 없음"}", fontSize = 12.sp)
            }
        }
    }
}
