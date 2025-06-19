package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "검색",
                tint = Color(0xFFA47148)
            )
        },
        placeholder = {
            Text(
                text = "검색어를 입력하세요",
                color = Color(0xFFBFA774),
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFF8E6),
            unfocusedContainerColor = Color(0xFFFFF8E6),
            disabledContainerColor = Color(0xFFFFF8E6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFA47148),
                shape = RoundedCornerShape(50)
            )
            .shadow(4.dp, RoundedCornerShape(50))
    )
}
