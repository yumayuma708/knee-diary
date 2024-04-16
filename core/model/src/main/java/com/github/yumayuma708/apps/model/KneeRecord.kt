package com.github.yumayuma708.apps.model

import java.util.Date

data class KneeRecord(
    val id: Long,
    val date: Date,
    val time: Date,
    val isRight: Boolean,
    val painLevel: Int,
    val whether: Int,
    val note: String,
    val createdAt: Date,
    val updatedAt: Date,
)
