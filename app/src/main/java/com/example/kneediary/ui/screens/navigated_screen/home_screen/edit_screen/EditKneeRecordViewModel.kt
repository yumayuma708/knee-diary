package com.example.kneediary.ui.screens.navigated_screen.home_screen.edit_screen

import androidx.lifecycle.ViewModel
import com.github.yumayuma708.apps.model.KneeRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditKneeRecordViewModel @Inject constructor(): ViewModel() {
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
}