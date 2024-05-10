package com.example.kneediary.ui.screens.navigated_screen.home_screen.edit_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.yumayuma708.apps.model.KneeNote
import com.repository.KneeNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditKneeNoteScreenViewModel @Inject constructor(
    private val repository: KneeNoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(){
    private val id: Long = savedStateHandle.get<Long>("kneeNoteId")
        ?: throw IllegalArgumentException("id is required")

    sealed interface UiState {
        data object Initial : UiState
        data object Loading : UiState
        data class LoadSuccess(val kneeNote: KneeNote) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
}