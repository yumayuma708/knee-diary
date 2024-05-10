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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.material3.Scaffold
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

@Composable
fun EditKneeNoteScreen(
    viewModel: EditKneeNoteScreenViewModel,
    back: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    EditKneeNoteScreen(
        uiState = uiState,
        load = {
            viewModel.load()
        },
        back = back,
        moveToIdle = {
            viewModel.moveToIdle()
        },
        update = { title, description, date, time ->
            viewModel.update(title, description, date, time)
        },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditKneeNoteScreen(
    uiState: EditKneeNoteScreenViewModel.UiState,
    back: () -> Unit,
    load: () -> Unit,
    moveToIdle: () -> Unit,
    update: (String, String, Long, Long) -> Unit,
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var showDateDialog by remember { mutableStateOf(false) }
    //dateは、kneenoteから受け取ったLong型の変数
    var date by remember { mutableStateOf(System.currentTimeMillis()) }
    //selectedDateは、DatePickerから受け取ったLocalDate!型の変数
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var datePicked by remember { mutableStateOf(false) }
    LaunchedEffect(uiState) {
        if (uiState is EditKneeNoteScreenViewModel.UiState.LoadSuccess && !datePicked) {
            date = uiState.kneeNote.date
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
        if (uiState is EditKneeNoteScreenViewModel.UiState.LoadSuccess && !timePicked) {
            time = uiState.kneeNote.time
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
            selectedTime.atDate(LocalDate.now()).atZone(ZoneId.of("UTC")).toInstant().toEpochMilli()
    }
    val changeTimeDialogState: () -> Unit = {
        showTimeDialog = !showTimeDialog
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.edit_knee_note))
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
                if (uiState is EditKneeNoteScreenViewModel.UiState.Idle) {
                    IconButton(onClick = {
                        update(title, description, date, time)
                    }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "保存")
                    }
                    IconButton(onClick = {
//                        showDeleteDialog()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "削除")
                    }
                }

            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        when (uiState) {
            EditKneeNoteScreenViewModel.UiState.Initial,
            EditKneeNoteScreenViewModel.UiState.Loading,
            is EditKneeNoteScreenViewModel.UiState.LoadSuccess -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }

            is EditKneeNoteScreenViewModel.UiState.LoadError -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    Text(uiState.error.toString())
                }
            }


            is EditKneeNoteScreenViewModel.UiState.Idle,
            is EditKneeNoteScreenViewModel.UiState.InputError,
            EditKneeNoteScreenViewModel.UiState.UpdateInProgress,
            EditKneeNoteScreenViewModel.UiState.UpdateSuccess,
            is EditKneeNoteScreenViewModel.UiState.UpdateError,
            is EditKneeNoteScreenViewModel.UiState.ConfirmDelete,
            is EditKneeNoteScreenViewModel.UiState.DeleteError,
            EditKneeNoteScreenViewModel.UiState.DeleteInProgress,
            EditKneeNoteScreenViewModel.UiState.DeleteSuccess,
            -> {
                EditKneeNoteForm(
                    modifier = Modifier.padding(innerPadding),

                    title = title,
                    setTitle = { title = it },

                    description = description,
                    setDescription = { description = it },

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
                )
                if (uiState is EditKneeNoteScreenViewModel.UiState.ConfirmDelete) {
                    AlertDialog(
                        onDismissRequest = {
                            moveToIdle()
                        },
                        text = {
                            Text(stringResource(R.string.delete_message))
                        },
                        confirmButton = {
                            TextButton(onClick = {
//                                delete()
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
            EditKneeNoteScreenViewModel.UiState.Initial -> {
                load()
            }

            is EditKneeNoteScreenViewModel.UiState.LoadError -> {

            }

            is EditKneeNoteScreenViewModel.UiState.LoadSuccess -> {
                title = uiState.kneeNote.title
                description = uiState.kneeNote.description
                date = uiState.kneeNote.date
                time = uiState.kneeNote.time
                moveToIdle()
            }

            EditKneeNoteScreenViewModel.UiState.Loading -> {

            }

            is EditKneeNoteScreenViewModel.UiState.Idle -> {

            }

            is EditKneeNoteScreenViewModel.UiState.InputError -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.title_empty),
                )
                moveToIdle()
            }

            EditKneeNoteScreenViewModel.UiState.UpdateInProgress -> {

            }

            EditKneeNoteScreenViewModel.UiState.UpdateSuccess -> {
                back()
            }

            is EditKneeNoteScreenViewModel.UiState.UpdateError -> {
                snackbarHostState.showSnackbar(
                    message = uiState.e.toString(),
                )
                moveToIdle()
            }

            is EditKneeNoteScreenViewModel.UiState.ConfirmDelete -> {

            }

            is EditKneeNoteScreenViewModel.UiState.DeleteError -> {
                snackbarHostState.showSnackbar(
                    message = uiState.e.toString(),
                )
                moveToIdle()
            }

            EditKneeNoteScreenViewModel.UiState.DeleteInProgress -> {

            }

            EditKneeNoteScreenViewModel.UiState.DeleteSuccess -> {
                back()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKneeNoteForm(
    modifier: Modifier = Modifier,

    title: String,
    setTitle: (String) -> Unit,

    description: String,
    setDescription: (String) -> Unit,

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

    ) {

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
            OutlinedTextField(
                value = title,
                onValueChange = setTitle,
                label = { Text("タイトル") },
                modifier = Modifier.height(screenHeight * 0.08f),
                maxLines = 1,
            )
            Box(modifier = Modifier.size(width = 20.dp, height = 10.dp))
            OutlinedTextField(
                value = description,
                onValueChange = setDescription,
                label = { Text("メモ") },
                modifier = Modifier.height(screenHeight * 0.45f),
            )
        }
    }
}