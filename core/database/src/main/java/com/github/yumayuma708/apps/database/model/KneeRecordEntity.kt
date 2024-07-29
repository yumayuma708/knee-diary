package com.github.yumayuma708.apps.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "knee_record",
)
data class KneeRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dateTime: LocalDateTime,
    val isRight: Boolean,
    val pain: Float,
    val weather: String,
    val note: String,
)
