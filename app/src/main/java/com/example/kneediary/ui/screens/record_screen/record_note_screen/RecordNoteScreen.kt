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
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kneediary.navigation.Nav
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordNoteScreen(
    navController: NavHostController,
) {
    var note by remember { mutableStateOf("") }
    var showDateDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var showTimeDialog by remember { mutableStateOf(false) }
    val timeFormatter = DateTimeFormatter.ofPattern("HH時mm分")
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Box(modifier = Modifier.size(width = 24.dp, height = 24.dp)) {}
                        Text(
                            "メモを入力します",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }) {
                        Icon(Icons.Rounded.Close, contentDescription = "閉じる")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxWidth().padding(start = 60.dp, end = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = { showDateDialog = true }
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
                            selectedDate.format(dateFormatter),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "表示",
                            modifier = commonSize
                        )
                        if (showDateDialog) {
                            val datePickerState =
                                rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
                            AlertDialog(
                                onDismissRequest = {
                                    showDateDialog = false
                                },
                                text = {
                                    DatePicker(
                                        state = datePickerState,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            showDateDialog = false
                                            selectedDate = Instant.ofEpochMilli(
                                                datePickerState.selectedDateMillis
                                                    ?: System.currentTimeMillis()
                                            ).atZone(ZoneId.systemDefault()).toLocalDate()
                                        }
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            showDateDialog = false

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
                    Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = { showTimeDialog = true }
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        val commonSize = Modifier.size(35.dp)
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = "カレンダー",
                            modifier = commonSize
                        )
                        Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                        Text(
                            selectedTime.format(timeFormatter),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "表示",
                            modifier = commonSize
                        )
                        if (showTimeDialog) {
                            val timePickerState = rememberTimePickerState()
                            AlertDialog(
                                onDismissRequest = {
                                    showTimeDialog = false
                                },
                                text = {
                                    TimePicker(
                                        state = timePickerState,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            showTimeDialog = false
                                            selectedTime = LocalTime.of(
                                                timePickerState.hour,
                                                timePickerState.minute
                                            )
                                        }
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            showTimeDialog = false
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
                    Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("メモ") },
                        placeholder = { Text("例：来週のどこかでリハビリセンターに行く。") },
                        modifier = Modifier.height(screenHeight * 0.55f)
                    )
                    Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigateUp()
                            //メモを保存する関数をViewModelに作成
                            //何かテキストフィールドに入力されていないと保存できないようにする。
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("保存", style = TextStyle(color = MaterialTheme.colorScheme.onPrimary))
                    }
                }
            }
        })
}

@Preview
@Composable
fun RecordNoteScreenPreview() {
    val navController = rememberNavController()
    RecordNoteScreen(navController = navController)
}