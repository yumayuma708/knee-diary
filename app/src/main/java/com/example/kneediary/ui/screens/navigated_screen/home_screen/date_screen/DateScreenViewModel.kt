package com.example.kneediary.ui.screens.navigated_screen.home_screen.date_screen

import FirestoreKneeRecordRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yumayuma708.apps.model.KneeRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

// @Injectアノテーションをつけ、constructorを指定し、@HiltViewModelアノテーションをつけることで、ViewModelをDIする。
@HiltViewModel
class DateScreenViewModel
@Inject
constructor(
    private val repository: FirestoreKneeRecordRepository,
) : ViewModel() {
    private val _items = MutableStateFlow<List<KneeRecord>>(emptyList())
    val items: StateFlow<List<KneeRecord>> = _items.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll()
                .catch { e ->
                    e.printStackTrace()
                }
                .collect {
                    _items.value = it
                }
        }
    }
}
