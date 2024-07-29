package com.github.yumayuma708.apps.model

import java.time.LocalDateTime

data class KneeRecord(
    val id: Long,
    val dateTime: LocalDateTime,
    val isRight: Boolean,
    val pain: Float,
    val weather: String,
    val note: String,
)
