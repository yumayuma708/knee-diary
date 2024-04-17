package com.repository

import com.github.yumayuma708.apps.database.dao.KneeRecordDao
import com.github.yumayuma708.apps.database.model.KneeRecordEntity
import com.github.yumayuma708.apps.model.KneeRecord
import java.util.Date
import javax.inject.Inject

//@Injectアノテーションをつけて、LocalKneeRecordRepositoryクラスをDI可能にしている。
//KneeRecordDaoの依存性を注入している。
class LocalKneeRecordRepository @Inject constructor(
    //privateで、このクラス内でしか使えないパラメータkneeRecordDaoを定義している。
    private val kneeRecordDao: KneeRecordDao,
): KneeRecordRepository {
    override suspend fun create(date: Long, time: Long, isRight: Boolean, painLevel: Int, weather: Int, note: String): KneeRecord {
        //currentTimeMillis()メソッドは、1970年1月1日00:00:00 GMTからの経過時間をミリ秒単位で返している。値はLong型
        val currentTime = System.currentTimeMillis()
        //kneeRecordEntityのインスタンスを作成
        //kneeRecordは、KneeRecordEntity型の変数。
        val kneeRecord = KneeRecordEntity(
            id = 0,
            date = date,
            time = time,
            isRight = isRight,
            painLevel = painLevel,
            weather = weather,
            note = note,
            createdAt = currentTime,
            updatedAt = currentTime,
        )
        val id = kneeRecordDao.create(kneeRecord)
        return KneeRecord(
            id =id,
            date = date,
            time = time,
            isRight = isRight,
            painLevel = painLevel,
            weather = weather,
            note = note,
            createdAt = kneeRecord.createdAt,
            updatedAt = kneeRecord.updatedAt,
        )
    }
}