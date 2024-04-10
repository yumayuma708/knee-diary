package com.example.kneediary.ui.screens.navigated_screen.home_screen.note_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoteScreen(modifier: Modifier = Modifier){
    Box (
        modifier = modifier
    ){
        Text(text = "ここにメモを表示させます")
    }
}
