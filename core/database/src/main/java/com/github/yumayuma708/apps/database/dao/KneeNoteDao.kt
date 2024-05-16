package com.github.yumayuma708.apps.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.yumayuma708.apps.database.model.KneeNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KneeNoteDao {
    @Insert
    suspend fun create(kneeNote: KneeNoteEntity): Long

    @Query("SELECT * FROM knee_note ORDER BY date DESC")
    fun getAll(): Flow<List<KneeNoteEntity>>

    @Query("SELECT * FROM knee_note WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): KneeNoteEntity?

    @Update
    suspend fun update(entity: KneeNoteEntity)
}