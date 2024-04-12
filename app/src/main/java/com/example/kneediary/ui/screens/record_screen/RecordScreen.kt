package com.example.kneediary.ui.screens.record_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kneediary.navigation.Nav
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
    val datePickerState = rememberDatePickerState()
    val now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
    val df = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    val fdate = df.format(now)
    val formattedDate: String = datePickerState.selectedDateMillis?.let {
        val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        localDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
    } ?: fdate
    var showDialog by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
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
                    Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = { showDialog = true }
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        val commonSize = Modifier.size(35.dp)
                        Icon(
                            imageVector = Icons.Rounded.Event,
                            contentDescription = "カレンダー",
                            modifier = commonSize
                        )
                        Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                        Text(
                            formattedDate,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "表示",
                            modifier = commonSize
                        )
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = {
                                    showDialog = false
                                },
                                text = {
                                    DatePicker(
                                        datePickerState = datePickerState,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            showDialog = false
                                        }
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            showDialog = false
                                        }
                                    ) {
                                        Text("キャンセル")
                                    }
                                },
                                properties = DialogProperties(
                                    usePlatformDefaultWidth = false
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(0.99f)
                                    .height(screenHeight * 0.75f)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRecordScreen() {
    val navController = rememberNavController()
    RecordScreen(navController = navController)
}
