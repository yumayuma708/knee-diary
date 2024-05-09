package com.example.kneediary.ui.screens.record_screen.record_note_screen

import androidx.lifecycle.ViewModel
import com.repository.KneeNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordNoteScreenViewModel@Inject constructor(
    private val kneeNoteRepository: KneeNoteRepository
): ViewModel() {
    fun create(
        title: String,
        description: String,
        date: Long,
        time: Long,
    ) {
    }
}