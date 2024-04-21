package com.repository

import com.github.yumayuma708.apps.database.dao.KneeRecordDao
import com.github.yumayuma708.apps.database.model.KneeRecordEntity
import com.github.yumayuma708.apps.model.KneeRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

//@Injectアノテーションをつけて、LocalKneeRecordRepositoryクラスをDI可能にしている。
//KneeRecordDaoの依存性を注入している。
class LocalKneeRecordRepository @Inject constructor(
    //privateで、このクラス内でしか使えないパラメータkneeRecordDaoを定義している。
    private val kneeRecordDao: KneeRecordDao,
): KneeRecordRepository {
    override suspend fun create(date: Long, time: Long, isRight: Boolean, painLevel: Int, weather: String, note: String): KneeRecord {
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

    //LocalKneeRecordRepositoryは、データベースからKneeRecordを取ってくるのが仕事
    //KneeRecordDaoを使って、Flow<List<KneeRecord>>を返すようにする
    //RoomのDaoは、Flowで結果を返す仕組みがあるので、その仕組みを使っている。
    override fun getAll(): Flow<List<KneeRecord>> {
        //KneeRecordDaoに、getAll()というメソッドを追加する
        return kneeRecordDao.getAll().map { items ->
            //map関数で、List<KneeRecordEntity>をList<KneeRecord>に変換する
            items.map { item ->
                KneeRecord(
                    id = item.id,
                    date = item.date,
                    time = item.time,
                    isRight = item.isRight,
                    painLevel = item.painLevel,
                    weather = item.weather,
                    note = item.note,
                    createdAt = item.createdAt,
                    updatedAt = item.updatedAt,
                )
            }
        }
    }
}