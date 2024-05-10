package com.example.kneediary.ui.screens.navigated_screen.home_screen.edit_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yumayuma708.apps.model.KneeNote
import com.repository.KneeNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditKneeNoteScreenViewModel @Inject constructor(
    private val repository: KneeNoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id: Long = savedStateHandle.get<Long>("kneeNoteId")
        ?: throw IllegalArgumentException("id is required")

    sealed interface UiState {
        data object Initial : UiState
        data object Loading : UiState
        data class LoadSuccess(val kneeNote: KneeNote) : UiState
        data class LoadError(val error: Throwable) : UiState
        data class Idle(val kneeNote: KneeNote) : UiState
        data class InputError(val kneeNote: KneeNote) : UiState
        data object UpdateInProgress : UiState
        data object UpdateSuccess : UiState
        data class UpdateError(val kneeNote: KneeNote, val e: Exception) : UiState
        data class ConfirmDelete(val kneeNote: KneeNote) : UiState
        data object DeleteInProgress : UiState
        data object DeleteSuccess : UiState
        data class DeleteError(val kneeNote: KneeNote, val e: Exception) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun load() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val kneeNote = repository.getById(id)
                if (kneeNote == null) {
                    _uiState.value =
                        UiState.LoadError(java.lang.IllegalArgumentException("KneeNote not found"))
                    return@launch
                }
                _uiState.value = UiState.LoadSuccess(kneeNote)
            } catch (e: Exception) {
                _uiState.value = UiState.LoadError(e)
            }
        }
    }

    fun moveToIdle() {
        val currentState = _uiState.value
        if (currentState is UiState.LoadSuccess) {
            _uiState.value = UiState.Idle(currentState.kneeNote)
        } else if (currentState is UiState.InputError) {
            _uiState.value = UiState.Idle(currentState.kneeNote)
        } else if (currentState is UiState.UpdateError) {
            _uiState.value = UiState.Idle(currentState.kneeNote)
        } else if (currentState is UiState.ConfirmDelete) {
            _uiState.value = UiState.Idle(currentState.kneeNote)
        } else if (currentState is UiState.DeleteError) {
            _uiState.value = UiState.Idle(currentState.kneeNote)
        }
    }

    fun update(
        title: String,
        description: String,
        date: Long,
        time: Long
    ) {
        val currentState = _uiState.value
        if (currentState !is UiState.Idle) {
            return
        }
        if (title.isEmpty()) {
            _uiState.value = UiState.InputError(currentState.kneeNote)
            return
        }
        _uiState.value = UiState.UpdateInProgress
        viewModelScope.launch {
            try {
                repository.update(
                    currentState.kneeNote.copy(
                        title = title,
                        description = description,
                        date = date,
                        time = time
                    )
                )
                _uiState.value = UiState.UpdateSuccess
            } catch (e: Exception) {
                _uiState.value = UiState.UpdateError(currentState.kneeNote, e)
            }
        }
    }


}

