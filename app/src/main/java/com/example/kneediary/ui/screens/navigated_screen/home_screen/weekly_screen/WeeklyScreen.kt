package com.example.kneediary.ui.screens.navigated_screen.home_screen.weekly_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeeklyScreen(modifier: Modifier = Modifier){
    Box (
        modifier = modifier
    ){
        Text(text = "ここに週ごとの痛みを表示させます")
    }
}
