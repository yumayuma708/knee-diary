package com.example.kneediary.ui.screens.record_screen.record_screen

import FirestoreKneeRecordRepository
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RecordScreenViewModel
@Inject
constructor(
    private val firestoreKneeRecordRepository: FirestoreKneeRecordRepository,
) : ViewModel() {

    // sealedがついているため、このインターフェイスの使用がこのモジュール内に制限される。
// また、このインターフェイス内の全ての可能性をコンパイラが考慮する。
// 例. when式で全ての可能性を網羅していない場合、コンパイルエラーが発生する。
    sealed interface UiState {
        // Idleは入力を受け付けている状態
        data object Idle : UiState

        data object InputError : UiState

        data object Success : UiState

        // CreateErrorは、例外の内容を持っておくと便利なので、data objectではなくdata classで作る
        data class CreateError(val e: Exception) : UiState
    }

    // ()の中が、初期化時点での値。つまり、UiStateの初期値は入力待ち状態
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)

    // Compose側から勝手に値の書き換えができないよう、uiStateをStateFlowとして作る
// .asStateFlowで、MutableStateFlowをStateFlowに変換している
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val db = Firebase.firestore

    private fun getTimestamp(localDateTime: LocalDateTime): Timestamp {
        val date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        return Timestamp(date)
    }

    fun saveData(
        dateTime: LocalDateTime,
        isRight: Boolean,
        pain: Float,
        weather: String,
        note: String,
    ) {
        if (weather.isEmpty()) {
            _uiState.value = UiState.InputError
            // returnは、create関数からの脱出を表す。この場合、InputErrorの時点でcreate関数を終了する。
            return
        }
        // KneeRecordRepositoryのcreate()を呼び出す
        // create function が suspend fun で非同期関数なので、viewModelScope.launch{}で呼び出す
        viewModelScope.launch {
            try {
                val timestamp = getTimestamp(dateTime)
                val data =
                    hashMapOf(
                        "dateTime" to timestamp,
                        "isRight" to isRight,
                        "pain" to pain,
                        "weather" to weather,
                        "note" to note,
                    )
//                    kneeRecordRepository.create(dateTime, isRight, pain, weather, note)
                db.collection("kneeRecord").add(data).addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.CreateError(e)
            }
        }
    }

    fun moveToIdle() {
        _uiState.value = UiState.Idle
    }
}
