package com.repository

import com.github.yumayuma708.apps.database.dao.KneeNoteDao
import com.github.yumayuma708.apps.database.model.KneeNoteEntity
import com.github.yumayuma708.apps.model.KneeNote
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
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

    override fun getAll(): Flow<List<KneeNote>> {
        return kneeNoteDao.getAll().map { items ->
            items.map { item -> item.toModel() }
        }
    }

    override suspend fun update(kneeNote: KneeNote) {
        val entity = KneeNoteEntity(
            id = kneeNote.id,
            title = kneeNote.title,
            description = kneeNote.description,
            date = kneeNote.date,
            time = kneeNote.time,
            createdAt = kneeNote.createdAt,
            updatedAt = System.currentTimeMillis(),
        )
        kneeNoteDao.update(entity)
    }
    override suspend fun getById(id: Long): KneeNote? {
        return kneeNoteDao.getById(id)?.toModel()
    }
}

private fun KneeNoteEntity.toModel(): KneeNote {
    return KneeNote(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}