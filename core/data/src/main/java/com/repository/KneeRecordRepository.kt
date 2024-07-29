package com.repository

import com.github.yumayuma708.apps.model.KneeRecord
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

// createメソッドは、KneeRecordオブジェクトを返す。
interface KneeRecordRepository {
    suspend fun create(
        dateTime: LocalDateTime,
        isRight: Boolean,
        pain: Float,
        weather: String,
        note: String,
    ): KneeRecord

    suspend fun getById(id: Long): KneeRecord?

    fun getAll(): Flow<List<KneeRecord>>

    suspend fun update(kneeRecord: KneeRecord)

    suspend fun delete(kneeRecord: KneeRecord)
}
