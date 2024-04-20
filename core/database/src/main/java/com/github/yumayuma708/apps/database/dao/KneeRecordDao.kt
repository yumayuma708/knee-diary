package com.github.yumayuma708.apps.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.github.yumayuma708.apps.database.model.KneeRecordEntity

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
}