package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = {
            Text("検索")
        },
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
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFA47148),
                shape = RoundedCornerShape(50)
            ),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    )
}
