package com.github.yumayuma708.apps.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(
    tableName = "knee_record",
)
data class KneeRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Long,
    val time: Long,
    val isRight: Boolean,
    val painLevel: Int,
    val weather: Int,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long,
)

//class DateTimeConverter{
//    @TypeConverter
//    fun fromTimestamp(value: Long?): DateTime? {
//        return if (value == null) null else Date(value)
//    }
//
//    @TypeConverter
//    fun toTimestamp(date: Date?): Long? {
//        return date?.toInstant()?.millis
//    }
//}