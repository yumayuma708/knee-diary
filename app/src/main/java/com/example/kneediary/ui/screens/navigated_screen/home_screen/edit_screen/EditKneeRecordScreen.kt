@file:Suppress("UNUSED_EXPRESSION")

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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.kneediary.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun EditKneeRecordScreen(
    viewModel: EditKneeRecordViewModel,
    back: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    EditKneeRecordScreen(
        uiState = uiState,
        load = {
            viewModel.load()
        },
        moveToIdle = {
            viewModel.moveToIdle()
        },
        back = back,
        update = { isRight, pain, weather, note, date, time ->
            viewModel.update(isRight, pain, weather, note, date, time)
        },
        showDeleteDialog = {
            viewModel.showDeleteDialog()
        },
        delete = {
            viewModel.delete()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditKneeRecordScreen(
    uiState: EditKneeRecordViewModel.UiState,
    load: () -> Unit,
    moveToIdle: () -> Unit,
    back: () -> Unit,
    update: (Boolean, Float, String, String, Long, Long) -> Unit,
    showDeleteDialog: () -> Unit,
    delete: () -> Unit,
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

    var showDateDialog by remember { mutableStateOf(false) }
    //dateは、kneeRecordから受け取ったLong型の変数
    var date by remember { mutableStateOf(System.currentTimeMillis()) }
    //selectedDateは、DatePickerから受け取ったLocalDate!型の変数
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var datePicked by remember { mutableStateOf(false) }
    LaunchedEffect(uiState) {
        if (uiState is EditKneeRecordViewModel.UiState.LoadSuccess && !datePicked) {
            date = uiState.kneeRecord.date
            selectedDate = Instant.ofEpochMilli(date).atZone(ZoneId.of("Asia/Tokyo")).toLocalDate()
        }
    }
    val setDate: (Long) -> Unit = { newDate ->
        date = newDate
        selectedDate = Instant.ofEpochMilli(newDate).atZone(ZoneId.of("Asia/Tokyo")).toLocalDate()
        showDateDialog = false
    }
    val changeDateDialogState: () -> Unit = {
        showDateDialog = !showDateDialog
    }

    var showTimeDialog by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf(System.currentTimeMillis()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var timePicked by remember { mutableStateOf(false) }
    LaunchedEffect(uiState) {
        if (uiState is EditKneeRecordViewModel.UiState.LoadSuccess && !timePicked) {
            time = uiState.kneeRecord.time
            selectedTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("Asia/Tokyo")).toLocalTime()
        } else {
            Instant.ofEpochMilli(time).atZone(ZoneId.of("Asia/Tokyo")).toLocalTime()
        }
    }
    val setTime: (LocalTime) -> Unit = { newTime ->
        selectedTime = newTime
        showTimeDialog = false
        timePicked = true
        time =
            selectedTime.atDate(LocalDate.now()).atZone(ZoneId.of("Asia/Tokyo")).toInstant().toEpochMilli()
    }
    val changeTimeDialogState: () -> Unit = {
        showTimeDialog = !showTimeDialog
    }

    var note by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.edit_knee_record))
            }, navigationIcon = {
                IconButton(
                    onClick = back
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "戻る"
                    )
                }
            }, actions = {
                if (uiState is EditKneeRecordViewModel.UiState.Idle) {
                    IconButton(onClick = {
                        update(isRight, pain, weather, note, date, time)
                    }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "保存")
                    }
                    IconButton(onClick = {
                        showDeleteDialog()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
                    }
                }

            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
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

            is EditKneeRecordViewModel.UiState.Idle,
            is EditKneeRecordViewModel.UiState.InputError,
            EditKneeRecordViewModel.UiState.UpdateInProgress,
            is EditKneeRecordViewModel.UiState.UpdateSuccess,
            is EditKneeRecordViewModel.UiState.UpdateError,
            is EditKneeRecordViewModel.UiState.ConfirmDelete,
            is EditKneeRecordViewModel.UiState.DeleteError,
            EditKneeRecordViewModel.UiState.DeleteInProgress,
            EditKneeRecordViewModel.UiState.DeleteSuccess
            -> {
                EditKneeRecordForm(
                    modifier = Modifier.padding(innerPadding),

                    isRight = isRight,
                    setIsRight = { isRight = !isRight },

                    pain = pain,
                    setPain = setPain,
                    sliderColor = sliderColor,

                    iconIds = iconIds,
                    selectedIconId = selectedIconId,
                    setWeather = setWeather,

                    date = date,
                    datePicked = datePicked,
                    selectedDate = selectedDate,
                    showDateDialog = showDateDialog,
                    changeDateDialogState = changeDateDialogState,
                    setDate = setDate,

                    time = time,
                    timePicked = timePicked,
                    selectedTime = selectedTime,
                    showTimeDialog = showTimeDialog,
                    changeTimeDialogState = changeTimeDialogState,
                    setTime = setTime,

                    note = note,
                    setNote = { note = it },
                )
                if (uiState is EditKneeRecordViewModel.UiState.ConfirmDelete) {
                    AlertDialog(
                        onDismissRequest = {
                        moveToIdle()
                        },
                        text = {
                            Text(stringResource(R.string.delete_message))
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                delete()
                            }) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                moveToIdle()
                            }) {
                                Text(stringResource(android.R.string.cancel))
                            }
                        },
                    )
                }
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
                date = uiState.kneeRecord.date
                time = uiState.kneeRecord.time
                note = uiState.kneeRecord.note
                moveToIdle()
            }

            EditKneeRecordViewModel.UiState.Loading -> {

            }

            is EditKneeRecordViewModel.UiState.Idle -> {

            }

            is EditKneeRecordViewModel.UiState.InputError -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.weather_empty),
                )
                moveToIdle()
            }

            EditKneeRecordViewModel.UiState.UpdateInProgress -> {

            }

            EditKneeRecordViewModel.UiState.UpdateSuccess -> {
                back()
            }

            is EditKneeRecordViewModel.UiState.UpdateError -> {
                snackbarHostState.showSnackbar(
                    message = uiState.e.toString(),
                )
                moveToIdle()
            }

            is EditKneeRecordViewModel.UiState.ConfirmDelete -> {

            }

            is EditKneeRecordViewModel.UiState.DeleteError -> {
                snackbarHostState.showSnackbar(
                    message = uiState.e.toString(),
                )
                moveToIdle()
            }
            EditKneeRecordViewModel.UiState.DeleteInProgress -> {

            }
            EditKneeRecordViewModel.UiState.DeleteSuccess -> {
                back()
            }
        }
    }
}

@Suppress("UNUSED_EXPRESSION")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKneeRecordForm(
    modifier: Modifier = Modifier,

    isRight: Boolean,
    setIsRight: () -> Unit,

    pain: Float,
    setPain: (Float) -> Unit,
    sliderColor: Color,

    iconIds: List<Int>,
    selectedIconId: Int?,
    setWeather: (Int) -> Unit,

    date: Long,
    datePicked: Boolean,
    selectedDate: LocalDate,
    showDateDialog: Boolean,
    changeDateDialogState: () -> Unit,
    setDate: (Long) -> Unit,

    time: Long,
    timePicked: Boolean,
    selectedTime: LocalTime,
    showTimeDialog: Boolean,
    changeTimeDialogState: () -> Unit,
    setTime: (LocalTime) -> Unit,

    note: String,
    setNote: (String) -> Unit,
) {

    val icons =
        listOf(Icons.Filled.WbSunny, Icons.Filled.Cloud, Icons.Filled.Umbrella, Icons.Filled.AcUnit)
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    val timeFormatter = DateTimeFormatter.ofPattern("HH時mm分")
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp


    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.padding(start = 60.dp, end = 60.dp)
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
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
                    IconButton(onClick = { setWeather(id) }) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            imageVector = icons[id],
                            contentDescription = null,
                            tint = if (selectedIconId == id) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.onSurface.copy(
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
                        onClick = changeDateDialogState
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
                    text = if (datePicked) {
                        selectedDate.format(dateFormatter)
                    } else {
                        Instant.ofEpochMilli(date).atZone(ZoneId.of("Asia/Tokyo")).toLocalDate()
                            .format(dateFormatter)

                    }, style = MaterialTheme.typography.titleMedium, maxLines = 1
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "表示",
                    modifier = commonSize
                )
                if (showDateDialog) {
                    val datePickerState =
                        rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
                    AlertDialog(onDismissRequest = {
                        changeDateDialogState
                    }, text = {
                        DatePicker(
                            state = datePickerState, modifier = Modifier.fillMaxWidth()
                        )
                    }, confirmButton = {
                        Button(onClick = {
                            datePickerState.selectedDateMillis?.let {
                                setDate(it)
                            }
                        }) {
                            Text("OK")
                        }
                    }, dismissButton = {
                        Button(
                            onClick = changeDateDialogState
                        ) {
                            Text("キャンセル")
                        }
                    }, properties = DialogProperties(
                        usePlatformDefaultWidth = false
                    ), modifier = Modifier
                        .fillMaxWidth(0.99f)
                        .height(screenHeight * 0.75f)
                    )
                }
            }
            Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = changeTimeDialogState
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
                    text = if (timePicked) {
                        selectedTime.format(timeFormatter)
                    } else {
                        Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC")).toLocalTime()
                            .format(timeFormatter)
                    }, style = MaterialTheme.typography.titleMedium, maxLines = 1
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "表示",
                    modifier = commonSize
                )
                if (showTimeDialog) {
                    val timePickerState = rememberTimePickerState()
                    AlertDialog(onDismissRequest = {
                        changeTimeDialogState
                    }, text = {
                        TimePicker(
                            state = timePickerState, modifier = Modifier.fillMaxWidth()
                        )
                    }, confirmButton = {
                        Button(onClick = {
                            val newTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            setTime(newTime)
                        }

                        ) {
                            Text("OK")
                        }
                    }, dismissButton = {
                        Button(
                            onClick = changeTimeDialogState
                        ) {
                            Text("キャンセル")
                        }
                    }, properties = DialogProperties(
                        usePlatformDefaultWidth = false
                    ), modifier = Modifier
                        .fillMaxWidth(0.99f)
                        .height(screenHeight * 0.75f)
                    )
                }
            }
            Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
            OutlinedTextField(value = note,
                onValueChange = setNote,
                label = { Text("メモ") },
                placeholder = { Text("例：右足の外側が痛い") },
                modifier = Modifier.height(screenHeight * 0.3f)
            )
            Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
        }
    }
}
