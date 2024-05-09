package com.github.yumayuma708.apps.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.github.yumayuma708.apps.database.model.KneeNoteEntity

@Dao
interface KneeNoteDao {
    @Insert
    suspend fun create(kneeNote: KneeNoteEntity): Long
}