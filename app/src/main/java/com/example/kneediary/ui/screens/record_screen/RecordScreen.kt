package com.example.kneediary.ui.screens.record_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kneediary.navigation.Nav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box {
                            Text("痛みを記録する")
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = 16.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(Nav.HomeScreen.name)
                                }) {
                                Icon(Icons.Rounded.Close, contentDescription = "閉じる")
                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                        ) {
                        Divider()
                        }
                    }
                },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {

            }
        }
    )
}


