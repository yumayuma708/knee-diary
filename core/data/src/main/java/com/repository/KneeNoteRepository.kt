package com.repository

import com.github.yumayuma708.apps.model.KneeNote

interface KneeNoteRepository {
    suspend fun create(
        title: String,
        description: String,
    ): KneeNote
}