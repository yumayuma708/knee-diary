package com.github.yumayuma708.apps.model


data class KneeRecord(
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
