package com.example.kneediary.ui.screens.record_screen.record_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
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
fun RecordScreen(
    back: () -> Unit,
    viewModel: RecordScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    // このRecordScreenも関数。
    // ViewModelを受け取っている方のRecordScreen関数なので、private funで作ったRecordScreen()を呼ぶだけにする。
    // ここでcreateを定義することにより、RecordScreenのUI部分のonClick処理でviewModel.createというように呼び出す必要がない。
    // これにより、RecordScreenのUI部分は、viewModelのことを知らなくても良い。
    // これを、カプセル化という。
    RecordScreen(
        // 左辺のuiStateに対して、CreateParameter 'uiState'を行い、
        // 自分で作ったViewModelのuiStateを一番上に移動させる。
        // こうすることで、UiStateが変化したタイミングで再婚ポーズが行われる。
        uiState = uiState,
        create = { date, time, isRight, pain, weather, note ->
            viewModel.create(date, time, isRight, pain, weather, note)
        },
        moveToIdle = {
            viewModel.moveToIdle()
        },
        back = back,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// private funで、引数が異なるRecordScreen()を作成
private fun RecordScreen(
    uiState: RecordScreenViewModel.UiState,
    create: (Long, Long, Boolean, Float, String, String) -> Unit,
    moveToIdle: () -> Unit,
    back: () -> Unit,
) {
    var isRight by remember { mutableStateOf(false) }

    var pain by remember { mutableStateOf(0f) }
    val customOrange = Color(1f, 165f / 255f, 0f)
    val sliderColor =
        when {
            pain < 0.25f -> Color.Blue
            pain < 0.5f -> Color.Green
            pain < 0.75f -> Color.Yellow
            pain < 1f -> customOrange
            else -> Color.Red
        }
    val iconIds = listOf(0, 1, 2, 3)
    var selectedIconId by remember { mutableStateOf<Int?>(null) }
    // weatherを定義
    val weather =
        when (selectedIconId) {
            0 -> "sunny"
            1 -> "cloudy"
            2 -> "rainy"
            3 -> "snowy"
            else -> ""
        }
    val icons =
        listOf(Icons.Filled.WbSunny, Icons.Filled.Cloud, Icons.Filled.Umbrella, Icons.Filled.AcUnit)

//    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf(LocalDate.now(ZoneId.of("Asia/Tokyo"))) }
    // dateをLong型で定義。このミリ秒は、選択された日付の午前0時からのミリ秒のこと。
    // これと、timeのミリ秒を足し合わせることで、日時を表す。
    val date: Long = selectedDate.atStartOfDay(ZoneId.of("Asia/Tokyo")).toInstant().toEpochMilli()
    var showDateDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")

    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    // timeをLong型で定義
    // これは、選択された時間の午前0時からのミリ秒のこと。
    // これと、dateのミリ秒を足し合わせることで、日時を表す。
    val time: Long = selectedTime.toNanoOfDay() / 1_000_000
    var showTimeDialog by remember { mutableStateOf(false) }
    val timeFormatter = DateTimeFormatter.ofPattern("HH時mm分")

    var note by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Box(modifier = Modifier.size(width = 24.dp, height = 24.dp)) {}
                        Text(
                            "痛みを記録する",
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = back) {
                        Icon(Icons.Rounded.Close, contentDescription = "閉じる")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(start = 60.dp, end = 60.dp),
            ) {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            RadioButton(selected = !isRight, onClick = { isRight = false })
                            Text("左足")
                            Box(modifier = Modifier.size(width = 20.dp, height = 20.dp))
                            RadioButton(selected = isRight, onClick = { isRight = true })
                            Text("右足")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) { Text("足の痛み") }
                        Slider(
                            value = pain,
                            onValueChange = { newValue ->
                                pain = newValue.coerceIn(0f, 4f)
                            },
                            steps = 3,
                            colors =
                                SliderDefaults.colors(
                                    activeTrackColor = sliderColor,
                                    thumbColor = sliderColor,
                                    inactiveTrackColor = sliderColor.copy(alpha = 0.24f),
                                ),
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
                                    },
                                ) {
                                    Icon(
                                        modifier =
                                            Modifier
                                                .size(48.dp),
                                        imageVector = icons[id],
                                        contentDescription = null,
                                        tint =
                                            if (selectedIconId == id) {
                                                MaterialTheme.colorScheme.primaryContainer
                                            } else {
                                                MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.6f,
                                                )
                                            },
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                        Row(
                            modifier =
                                Modifier
                                    .clickable(
                                        onClick = { showDateDialog = true },
                                    )
                                    .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            val commonSize = Modifier.size(35.dp)
                            Icon(
                                imageVector = Icons.Rounded.Event,
                                contentDescription = "カレンダー",
                                modifier = commonSize,
                            )
                            Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))

                            // ////////////////////////////////////////////////////
                            Text(
                                // LocalDate!型のselectedDateを〇〇年〇〇月〇〇日に変換
                                selectedDate.format(dateFormatter),
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                            )
                            // ////////////////////////////////////////////////////

                            Icon(
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = "表示",
                                modifier = commonSize,
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
                                            modifier = Modifier.fillMaxWidth(),
                                        )
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                showDateDialog = false
                                                selectedDate =
                                                    Instant.ofEpochMilli(
                                                        datePickerState.selectedDateMillis
                                                            ?: System.currentTimeMillis(),
                                                    ).atZone(ZoneId.of("Asia/Tokyo")).toLocalDate()
                                            },
                                        ) {
                                            Text("OK")
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = {
                                                showDateDialog = false
                                            },
                                        ) {
                                            Text("キャンセル")
                                        }
                                    },
                                    properties =
                                        DialogProperties(
                                            usePlatformDefaultWidth = false,
                                        ),
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(0.99f)
                                            .height(screenHeight * 0.75f),
                                )
                            }
                        }
                        Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                        Row(
                            modifier =
                                Modifier
                                    .clickable(
                                        onClick = { showTimeDialog = true },
                                    )
                                    .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            val commonSize = Modifier.size(35.dp)
                            Icon(
                                imageVector = Icons.Rounded.Schedule,
                                contentDescription = "カレンダー",
                                modifier = commonSize,
                            )
                            Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                            Text(
                                selectedTime.format(timeFormatter),
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                            )
                            Icon(
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = "表示",
                                modifier = commonSize,
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
                                            modifier = Modifier.fillMaxWidth(),
                                        )
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                showTimeDialog = false
                                                selectedTime =
                                                    LocalTime.of(
                                                        timePickerState.hour,
                                                        timePickerState.minute,
                                                    )
                                            },
                                        ) {
                                            Text("OK")
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = {
                                                showTimeDialog = false
                                            },
                                        ) {
                                            Text("キャンセル")
                                        }
                                    },
                                    properties =
                                        DialogProperties(
                                            usePlatformDefaultWidth = false,
                                        ),
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(0.99f)
                                            .height(screenHeight * 0.75f),
                                )
                            }
                        }
                        Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text("メモ") },
                            placeholder = { Text("例：右足の外側が痛い") },
                            modifier = Modifier.height(screenHeight * 0.3f),
                        )
                        Box(modifier = Modifier.size(width = 20.dp, height = 30.dp))
                        OutlinedButton(
                            onClick = {
                                create(date, time, isRight, pain, weather, note)
                            },
                            colors =
                                ButtonDefaults.outlinedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                ),
                        ) {
                            Text(
                                "保存",
                                style = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                            )
                        }
                    }
                }
            }
        },
    )

    LaunchedEffect(uiState) {
        when (uiState) {
            RecordScreenViewModel.UiState.Idle -> {
                // 何もしない
            }

            RecordScreenViewModel.UiState.InputError -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.weather_empty),
                )
                moveToIdle()
            }

            RecordScreenViewModel.UiState.Success -> {
                back()
            }

            is RecordScreenViewModel.UiState.CreateError -> {
                snackbarHostState.showSnackbar(
                    message = uiState.e.toString(),
                )
                moveToIdle()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RecordScreen(
        uiState = RecordScreenViewModel.UiState.Idle,
        back = {},
        create = { _, _, _, _, _, _ -> },
        moveToIdle = {},
    )
}
