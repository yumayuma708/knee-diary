package com.repository

import com.github.yumayuma708.apps.model.KneeRecord

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
}