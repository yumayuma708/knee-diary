package com.example.kneediary.ui.screens.navigated_screen.home_screen.note_screen

import androidx.lifecycle.ViewModel
import com.repository.KneeNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel @Inject constructor(
    private val repository: KneeNoteRepository
): ViewModel(){
    val items = repository.getAll()
}