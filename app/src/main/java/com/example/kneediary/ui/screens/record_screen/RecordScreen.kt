package com.example.kneediary.ui.screens.record_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecordScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Text(text = "ここで膝の痛みを記録します")
    }
}

