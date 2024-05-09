package com.repository

import com.github.yumayuma708.apps.database.dao.KneeNoteDao
import com.github.yumayuma708.apps.database.model.KneeNoteEntity
import com.github.yumayuma708.apps.model.KneeNote
import javax.inject.Inject

class LocalKneeNoteRepository @Inject constructor(
    private val kneeNoteDao: KneeNoteDao,
) : KneeNoteRepository{
    override suspend fun create(
        title: String,
        description: String,
        date: Long,
        time: Long,
    ): KneeNote {
        val currentTime = System.currentTimeMillis()
        val kneeNote = KneeNoteEntity(
            id = 0,
            title = title,
            description = description,
            date = date,
            time = time,
            createdAt = currentTime,
            updatedAt = currentTime,

        )
        val id = kneeNoteDao.create(kneeNote)
        return KneeNote(
            id = id,
            title = title,
            description = description,
            date = date,
            time = time,
            createdAt = kneeNote.createdAt,
            updatedAt = kneeNote.updatedAt,
        )
    }
}