package com.example.kneediary.ui.screens.record_screen.record_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repository.KneeRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RecordScreenViewModel @Inject constructor(
    private val kneeRecordRepository: KneeRecordRepository
): ViewModel() {
    //sealedがついているため、このインターフェイスの使用がこのモジュール内に制限される。
    //また、このインターフェイスないの全ての可能性をコンパイラが考慮する。
    //例. when式で全ての可能性を網羅していない場合、コンパイルエラーが発生する。
    sealed interface UiState {
        //Idleは入力を受け付けている状態
        data object Idle : UiState
        data object InputError : UiState
        data object Success : UiState

        //CreateErrorは、例外の内容を持っておくと便利なので、data objectではなくdata classで作る
        data class CreateError(val e: Exception) : UiState
    }
    //()の中が、初期化時点での値。つまり、UiStateの初期値は入力待ち状態
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)

    //Compose側から勝手に値の書き換えができないよう、uiStateをStateFlowとして作る
    //.asStateFlowで、MutableStateFlowをStateFlowに変換している
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    fun create(
        date: Long,
        time: Long,
        isRight: Boolean,
        painLevel: Int,
        weather: String,
        note: String,
    ) {
        if (weather.isEmpty()) {
            _uiState.value = UiState.InputError
            //returnは、create関数からの脱出を表す。この場合、InputErrorの時点でcreate関数を終了する。
            return
        }
        //KneeRecordRepositoryのcreate()を呼び出す
        //create function が suspend fun で非同期関数なので、viewModelScope.launch{}で呼び出す
        viewModelScope.launch {
            try {
                kneeRecordRepository.create(date, time, isRight, painLevel, weather, note)
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.CreateError(e)
            }
        }
    }
}