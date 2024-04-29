package com.github.yumayuma708.apps.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.yumayuma708.apps.database.model.KneeRecordEntity
import kotlinx.coroutines.flow.Flow

//DaoはDatabase Access Objectの略で、データベースに対してCURD操作(create,update,read,delete)を行うためのインターフェースのこと。
@Dao
interface KneeRecordDao {
    //@Insertアノテーションをつけて、データを追加するメソッドを定義する。
    //create関数がそのメソッド。：Longで、追加した後にIDとしてLong型の値を返す。
    //createの中身は、パラメータ(引数)。
    //ここでいう引数の中身は、KneeRecordEntity型のkneeRecordという変数。
    //このcreateメソッドは、repositoryクラスのcreateメソッドとは別物。
    @Insert
    suspend fun create(kneeRecord:KneeRecordEntity): Long

    @Query("SELECT * FROM knee_record WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): KneeRecordEntity?


    @Query("SELECT * FROM knee_record order by updatedAt desc")  //一番最新のものが一番最初にくるような順番でデータを取得する。このSQLを書くことでRoomが勝手にデータを取得してこの順番で表示してくれる。データに追加や更新があった場合も、自動で更新してくれる。
    fun getAll(): Flow<List<KneeRecordEntity>>  //戻り値の型は、:core:databaseモジュールでKneeRecordEntityを扱うようにしているため、Flow<List<KneeRecordEntity>>になっている。
}