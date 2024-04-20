package com.github.yumayuma708.apps.model


data class KneeRecord(
    val id: Long,
    val date: Long,
    val time: Long,
    val isRight: Boolean,
    val painLevel: Int,
    val weather: String,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long,
)
