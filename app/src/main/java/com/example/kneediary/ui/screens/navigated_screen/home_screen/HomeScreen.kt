@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kneediary.ui.screens.navigated_screen.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen.DateScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
){
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("日", "週", "メモ")
    val icons = listOf(Icons.Filled.Today, Icons.Filled.DateRange,
        Icons.Filled.Description
    )
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
            DateScreen(modifier = Modifier.padding(innerPadding))
        },
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                    }
                }
            }
        }
    )
}
