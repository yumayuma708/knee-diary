package com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DateScreen(modifier: Modifier = Modifier){
        Box (
            modifier = modifier
        ){
            Text(text = "ここに毎日の痛みを表示させます")
        }
    }
