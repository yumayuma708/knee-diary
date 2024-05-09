package com.example.kneediary.ui.screens.record_screen.record_note_screen

import androidx.lifecycle.ViewModel
import com.repository.KneeNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecordNoteScreenViewModel@Inject constructor(
    private val kneeNoteRepository: KneeNoteRepository
): ViewModel() {
    sealed interface UiState {
        data object Idle : UiState
        data object InputError : UiState
        data object Success : UiState
        data class CreateError(val e: Exception) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    fun create(
        title: String,
        description: String,
        date: Long,
        time: Long,
    ) {
    }
}