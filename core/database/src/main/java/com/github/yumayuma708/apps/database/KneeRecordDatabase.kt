package com.github.yumayuma708.apps.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.yumayuma708.apps.database.dao.KneeRecordDao
import com.github.yumayuma708.apps.database.model.KneeRecordEntity

@Database(
    entities = [
        KneeRecordEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class KneeRecordDatabase : RoomDatabase() {
    abstract fun kneeRecordDao(): KneeRecordDao
}