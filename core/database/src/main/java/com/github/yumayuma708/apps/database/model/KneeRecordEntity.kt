package com.github.yumayuma708.apps.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "knee_record",
)
data class KneeRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Long,
    val time: Long,
    val isRight: Boolean,
    val pain: Float,
    val weather: String,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long,
)
