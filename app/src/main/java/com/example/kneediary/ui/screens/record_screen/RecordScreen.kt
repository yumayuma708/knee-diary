package com.example.kneediary.ui.screens.record_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kneediary.navigation.Nav
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavHostController,
) {
    var state by remember { mutableStateOf(true) }
    var pain by remember { mutableStateOf(0f) }
    val customOrange = Color(1f, 165f / 255f, 0f)
    val sliderColor = when {
        pain < 0.25f -> Color.Blue
        pain < 0.5f -> Color.Green
        pain < 0.75f -> Color.Yellow
        pain < 1f -> customOrange
        else -> Color.Red
    }
    var selectedIconId by remember { mutableStateOf<Int?>(null) }
    val icons =
        listOf(Icons.Filled.WbSunny, Icons.Filled.Cloud, Icons.Filled.Umbrella, Icons.Filled.AcUnit)
    val iconIds = listOf(0, 1, 2, 3)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "痛みを記録する",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Nav.HomeScreen.name)
                        }) {
                        Icon(Icons.Rounded.Close, contentDescription = "閉じる")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(start = 60.dp, end = 60.dp)
            ) {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(selected = state, onClick = { state = true })
                        Text("左足")
                        Box(modifier = Modifier.size(width = 20.dp, height = 20.dp))
                        RadioButton(selected = !state, onClick = { state = false })
                        Text("右足")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) { Text("足の痛み") }
                    Slider(
                        value = pain,
                        onValueChange = { newValue ->
                            pain = newValue.coerceIn(0f, 4f)
                        },
                        steps = 3,
                        colors = SliderDefaults.colors(
                            activeTrackColor = sliderColor,
                            thumbColor = sliderColor,
                            inactiveTrackColor = sliderColor.copy(alpha = 0.24f)
                        )
                    )
                    Text(text = ((pain * 4).roundToInt() + 1).toString())
                    Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ){
                        iconIds.forEach { id ->
                            IconButton(
                                onClick = {
                                    selectedIconId = id
                                }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(48.dp),
                                    imageVector = icons[id],
                                    contentDescription = null,
                                    tint = if (selectedIconId == id) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.6f
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


