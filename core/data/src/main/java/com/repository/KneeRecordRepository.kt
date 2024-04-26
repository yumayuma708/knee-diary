package com.repository

import com.github.yumayuma708.apps.model.KneeRecord
import kotlinx.coroutines.flow.Flow

//createメソッドは、KneeRecordオブジェクトを返す。
interface KneeRecordRepository {
    suspend fun create(
        date: Long,
        time: Long,
        isRight: Boolean,
        painLevel: Int,
        weather: String,
        note: String
    ): KneeRecord

    suspend fun getById(id: Long): KneeRecord?

    fun getAll(): Flow<List<KneeRecord>>
}