@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kneediary.ui.screens.navigated_screen.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.ai.client.generativeai.type.content

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "ひざ日記",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
        Box (
            modifier = Modifier.padding(innerPadding)
        ){
            Text(text = "ここはホーム画面です")
        }
        }
    )
}
