package com.example.kneediary.ui.screens.navigated_screen.home_screen.edit_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun EditKneeRecordScreen(
    viewModel: EditKneeRecordViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    EditKneeRecordScreen(
        uiState = uiState,
        load = {
            viewModel.load()
        },
        moveToIdle = {
            viewModel.moveToIdle()
        }
    )
}

@Composable
private fun EditKneeRecordScreen(
    uiState: EditKneeRecordViewModel.UiState,
    load: () -> Unit,
    moveToIdle: () -> Unit
) {
    var isRight by remember { mutableStateOf(false) }
    var pain by remember { mutableStateOf(0f) }
    val customOrange = Color(1f, 165f / 255f, 0f)
    val sliderColor = when {
        pain < 0.25f -> Color.Blue
        pain < 0.5f -> Color.Green
        pain < 0.75f -> Color.Yellow
        pain < 1f -> customOrange
        else -> Color.Red
    }
    val setPain: (Float) -> Unit = { newValue ->
        pain = newValue
        println("Updated pain: $pain")
    }

    val iconIds = listOf(0, 1, 2, 3)
    var selectedIconId by remember { mutableStateOf<Int?>(null) }
    val setWeather: (Int) -> Unit = { id ->
        selectedIconId = id
    }
    val weather = when (selectedIconId) {
        0 -> "sunny"
        1 -> "cloudy"
        2 -> "rainy"
        3 -> "snowy"
        else -> ""
    }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val date: Long = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val time: Long = selectedTime.toNanoOfDay() / 1_000_000
    var note by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        when (uiState) {
            EditKneeRecordViewModel.UiState.Initial,
            EditKneeRecordViewModel.UiState.Loading,
            is EditKneeRecordViewModel.UiState.LoadSuccess -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }

            is EditKneeRecordViewModel.UiState.LoadError -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    Text(uiState.error.toString())
                }
            }

            EditKneeRecordViewModel.UiState.Idle -> {
                EditKneeRecordForm(
                    modifier = Modifier.padding(innerPadding),
                    isRight = isRight,
                    setIsRight = { isRight = !isRight },
                    note = note,
                    setNote = { note = it },
                    date = date,
                    iconIds = iconIds,
                    selectedIconId = selectedIconId,
                    weather = weather,
                    setWeather = setWeather,
                    pain = pain,
                    setPain = setPain,
                    sliderColor = sliderColor,
                )
            }

        }


    }
    LaunchedEffect(uiState) {
        when (uiState) {
            EditKneeRecordViewModel.UiState.Initial -> {
                load()
            }

            is EditKneeRecordViewModel.UiState.LoadError -> {

            }

            is EditKneeRecordViewModel.UiState.LoadSuccess -> {
                isRight = uiState.kneeRecord.isRight
                pain = uiState.kneeRecord.pain
                selectedIconId = when (uiState.kneeRecord.weather) {
                    "sunny" -> 0
                    "cloudy" -> 1
                    "rainy" -> 2
                    "snowy" -> 3
                    else -> null
                }
                selectedDate =
                    Instant.ofEpochMilli(uiState.kneeRecord.date).atZone(ZoneId.systemDefault())
                        .toLocalDate()
                selectedTime = LocalTime.ofNanoOfDay(uiState.kneeRecord.time * 1_000_000)
                note = uiState.kneeRecord.note
                moveToIdle()
            }

            EditKneeRecordViewModel.UiState.Loading -> {

            }

            EditKneeRecordViewModel.UiState.Idle -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKneeRecordForm(
    modifier: Modifier = Modifier,
    date: Long,
    note: String,
    setNote: (String) -> Unit,
    isRight: Boolean,
    setIsRight: () -> Unit,
    iconIds: List<Int>,
    selectedIconId: Int?,
    weather: String,
    setWeather: (Int) -> Unit,
    pain: Float,
    setPain: (Float) -> Unit,
    sliderColor: Color,
) {

    val icons =
        listOf(Icons.Filled.WbSunny, Icons.Filled.Cloud, Icons.Filled.Umbrella, Icons.Filled.AcUnit)
    var showDateDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    var showTimeDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val timeFormatter = DateTimeFormatter.ofPattern("HH時mm分")
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 60.dp, end = 60.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(selected = !isRight, onClick = setIsRight)
                Text("左足")
                Box(modifier = Modifier.size(width = 20.dp, height = 20.dp))
                RadioButton(selected = isRight, onClick = setIsRight)
                Text("右足")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) { Text("足の痛み") }
            Slider(
                value = pain,
                onValueChange = setPain,
                steps = 3,
                valueRange = 0f..1f,
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
                        onClick = { setWeather(id) }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp),
                            imageVector = icons[id],
                            contentDescription = null,
                            tint = if (selectedIconId == id)
                                MaterialTheme
                                    .colorScheme
                                    .primaryContainer
                            else MaterialTheme
                                .colorScheme
                                .onSurface
                                .copy(
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
                onValueChange = setNote,
                label = { Text("メモ") },
                placeholder = { Text("例：右足の外側が痛い") },
                modifier = Modifier.height(screenHeight * 0.3f)
            )
            Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
        }
    }
}
