package com.example.event_master_tarea2_vlillog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CategoryItem(
    category: Category,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(24.dp)
    ) {

        Text(
            text = category.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

    }

}