package com.repository

import com.github.yumayuma708.apps.model.KneeNote
import kotlinx.coroutines.flow.Flow

interface KneeNoteRepository {
    suspend fun create(
        title: String,
        description: String,
        date: Long,
        time: Long,
    ): KneeNote

    fun getAll(): Flow<List<KneeNote>>
}