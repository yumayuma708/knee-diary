package com.github.yumayuma708.apps.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.yumayuma708.apps.database.dao.KneeNoteDao
import com.github.yumayuma708.apps.database.model.KneeNoteEntity

@Database(
    entities = [
        KneeNoteEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class KneeNoteDatabase : RoomDatabase() {
    abstract fun kneeNoteDao(): KneeNoteDao
}