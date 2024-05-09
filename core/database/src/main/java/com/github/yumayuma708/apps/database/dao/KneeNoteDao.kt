package com.github.yumayuma708.apps.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.yumayuma708.apps.database.model.KneeNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KneeNoteDao {
    @Insert
    suspend fun create(kneeNote: KneeNoteEntity): Long

    @Query("SELECT * FROM knee_record ORDER BY date DESC")
    fun getAll(): Flow<List<KneeNoteEntity>>
}