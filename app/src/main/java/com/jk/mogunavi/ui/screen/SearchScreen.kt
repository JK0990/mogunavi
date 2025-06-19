package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.mogunavi.R

@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로고
        Image(
            painter = painterResource(id = R.drawable.mogunavi_logo),
            contentDescription = "로고",
            modifier = Modifier
                .size(196.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(0.dp, Color.Transparent),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 검색 바
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("검색어를 입력하세요") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "검색 아이콘",
                    tint = Color(0xFFA47148)
                )
            },
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFFFF8E6),
                focusedContainerColor = Color(0xFFFFF8E6),
                disabledContainerColor = Color(0xFFFFF8E6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFA47148),
                    shape = RoundedCornerShape(50)
                )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 임시 텍스트
        Text(
            text = "검색어: $query",
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
        )
    }
}
