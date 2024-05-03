package com.example.kneediary.ui.screens.navigated_screen.home_screen.edit_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yumayuma708.apps.model.KneeRecord
import com.repository.KneeRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//ViewModelでは、KneeRecordRepositoryを使ってKneeRecordを取ってくる。
class EditKneeRecordViewModel @Inject constructor(
    //KneeRecordRepositoryを、constructorを通じて注入している
    private val repository: KneeRecordRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id: Long = savedStateHandle.get<Long>("kneeRecordId")
        ?: throw IllegalArgumentException("id is required")

    sealed interface UiState {
        data object Initial : UiState
        data object Loading : UiState
        data class LoadSuccess(val kneeRecord: KneeRecord) : UiState
        data class LoadError(val error: Throwable) : UiState
        data class Idle(val kneeRecord: KneeRecord) : UiState
        data class InputError(val kneeRecord: KneeRecord) : UiState
        data object UpdateInProgress : UiState
        data object UpdateSuccess : UiState
        data class UpdateError(val kneeRecord: KneeRecord, val e: Exception) : UiState
        data class ConfirmDelete(val kneeRecord: KneeRecord) : UiState
        data object DeleteInProgress : UiState
        data object DeleteSuccess : UiState
        data class DeleteError(val kneeRecord: KneeRecord, val e: Exception) : UiState
    }

    //UiState型のMutableStateFlowを作成し、初期値をUiState.Initialに設定している。
    //MutableStateFlowは、値を変更できる.StateFlowは、値を変更できない.
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)

    //asStateFlow()で、MutableStateFlowの読み取り専用のビューを提供している。
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun load() {
        //UiStateをLoadingに変更してから、repositoryからID経由でkneeRecordを取得するようにする。
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val kneeRecord = repository.getById(id)
                if (kneeRecord == null) {
                    _uiState.value =
                        UiState.LoadError(java.lang.IllegalArgumentException("KneeRecord not found"))
                    return@launch
                }
                _uiState.value = UiState.LoadSuccess(kneeRecord)
            } catch (e: Exception) {
                _uiState.value = UiState.LoadError(e)
            }
        }
    }

    fun moveToIdle() {
        val currentState = _uiState.value
        if (currentState is UiState.LoadSuccess) {
            _uiState.value = UiState.Idle(currentState.kneeRecord)
        } else if (currentState is UiState.InputError) {
            _uiState.value = UiState.Idle(currentState.kneeRecord)
        } else if (currentState is UiState.UpdateError) {
            _uiState.value = UiState.Idle(currentState.kneeRecord)
        } else if (currentState is UiState.ConfirmDelete) {
            _uiState.value = UiState.Idle(currentState.kneeRecord)
        } else if (currentState is UiState.DeleteError) {
            _uiState.value = UiState.Idle(currentState.kneeRecord)
        }
    }

    fun update(
        isRight: Boolean,
        pain: Float,
        weather: String,
        note: String,
        date: Long,
        time: Long
    ) {
        val currentState = _uiState.value
        if (currentState !is UiState.Idle) {
            return
        }
        if (weather.isEmpty()) {
            _uiState.value = UiState.InputError(currentState.kneeRecord)
            return
        }
        _uiState.value = UiState.UpdateInProgress
        viewModelScope.launch {
            try {
                repository.update(
                    currentState.kneeRecord.copy(
                        isRight = isRight,
                        pain = pain,
                        weather = weather,
                        note = note,
                        date = date,
                        time = time
                    )
                )
                _uiState.value = UiState.UpdateSuccess
            } catch (e: Exception) {
                _uiState.value = UiState.UpdateError(currentState.kneeRecord, e)
            }
        }
    }

    fun showDeleteDialog() {
        val currentState = _uiState.value
        if (currentState !is UiState.Idle) {
            return
        }
        _uiState.value = UiState.ConfirmDelete(currentState.kneeRecord)
    }

    fun delete() {
        val currentState = _uiState.value
        if (currentState !is UiState.ConfirmDelete) {
            return
        }
        _uiState.value = UiState.DeleteInProgress
        viewModelScope.launch {
            try {
                repository.delete(currentState.kneeRecord)
                _uiState.value = UiState.DeleteSuccess
            } catch (e: Exception) {
                _uiState.value = UiState.DeleteError(currentState.kneeRecord, e)
            }
        }
    }
}