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
): ViewModel() {
    private val id: Long = savedStateHandle.get<Long>("kneeRecordId") ?: throw IllegalArgumentException("id is required")

    sealed interface UiState {
        data object Initial: UiState
        data object Loading: UiState
        data class LoadSuccess(val kneeRecord: KneeRecord): UiState
        data class LoadError(val error: Throwable): UiState
        data object Idle: UiState
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
                    _uiState.value = UiState.LoadError(java.lang.IllegalArgumentException("KneeRecord not found"))
                    return@launch
                }
                _uiState.value = UiState.LoadSuccess(kneeRecord)
            } catch (e: Exception) {
                _uiState.value = UiState.LoadError(e)
            }
        }
    }

    fun moveToIdle() {
        _uiState.value = UiState.Idle
    }
}