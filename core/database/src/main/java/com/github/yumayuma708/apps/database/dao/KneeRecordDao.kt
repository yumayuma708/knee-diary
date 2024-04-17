package com.github.yumayuma708.apps.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.github.yumayuma708.apps.database.model.KneeRecordEntity

@Dao
interface KneeRecordDao {
    @Insert
    suspend fun create(kneeRecord:KneeRecordEntity): Long
}