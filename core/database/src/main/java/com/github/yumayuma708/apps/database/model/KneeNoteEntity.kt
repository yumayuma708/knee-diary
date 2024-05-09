package com.github.yumayuma708.apps.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "knee_note",
)
data class KneeNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val date: Long,
    val time: Long,
    val createdAt: Long,
    val updatedAt: Long,
)