package com.example.kneediary.ui.screens.navigated_screen.home_screen.edit_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun EditKneeNoteScreen(
    viewModel: EditKneeNoteScreenViewModel,
    back: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    EditKneeNoteScreen(
        uiState = uiState,
        onBack = back,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditKneeNoteScreen(
    uiState: EditKneeNoteScreenViewModel.UiState,
    onBack: () -> Unit,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKneeNoteForm(

) {

}