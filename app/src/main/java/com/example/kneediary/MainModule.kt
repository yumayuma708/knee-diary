package com.example.kneediary

import android.content.Context
import androidx.room.Room
import com.github.yumayuma708.apps.database.KneeRecordDatabase
import com.github.yumayuma708.apps.database.dao.KneeRecordDao
import com.repository.KneeRecordRepository
import com.repository.LocalKneeRecordRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    //kneeRecordDaoとkneeRecordDatabaseの作り方を教える
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KneeRecordDatabase {
        //Roomを使ってToDoDatabaseを作る
        return Room.databaseBuilder(
            context,
            KneeRecordDatabase::class.java, //どういうデータベースを作るか
            "knee_record.db", //データベースの名前
        ).build()
    }

    @Provides
    @Singleton
    //引数でKneeRecordDatabaseを受け取っている。
    fun provideKneeRecordDao(db: KneeRecordDatabase): KneeRecordDao { //  : KneeRecordDaoというのは、この関数の戻り値の方がKneeRecordDaoだということ。
        return db.kneeRecordDao()  // dbには、KneeRecordDaoというメソッドがある。実際に飛んでみると、abstract funで定義されている。
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun bindKneeRecordRepository(
        impl: LocalKneeRecordRepository
    ): KneeRecordRepository
}