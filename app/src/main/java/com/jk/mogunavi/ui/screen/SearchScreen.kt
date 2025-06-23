package com.jk.mogunavi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }

    val distanceOptions = listOf("500m", "1km", "2km", "3km")
    val rangeMap = mapOf("500m" to 2, "1km" to 3, "2km" to 4, "3km" to 5)
    var selectedDistance by remember { mutableStateOf("1km") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("検索") },
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

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            distanceOptions.forEach { label ->
                OutlinedButton(
                    onClick = { selectedDistance = label },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedDistance == label) Color(0xFFA47148) else Color.White,
                        contentColor = if (selectedDistance == label) Color.White else Color(0xFFA47148)
                    ),
                    contentPadding = PaddingValues(vertical = 6.dp, horizontal = 0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = if (label == "500m") 12.sp else 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "검색어: $query, 거리: ${selectedDistance}",
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
        )
    }
}
