package com.example.kneediary

import FirestoreKneeRecordRepository
import android.content.Context
import androidx.room.Room
import com.github.yumayuma708.apps.database.KneeNoteDatabase
import com.github.yumayuma708.apps.database.dao.KneeNoteDao
import com.repository.KneeNoteRepository
import com.repository.LocalFirestoreKneeRecordRepository
import com.repository.LocalKneeNoteRepository
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
    @Provides
    @Singleton
    fun provideDatabase2(
        @ApplicationContext context: Context,
    ): KneeNoteDatabase {
        return Room.databaseBuilder(
            context,
            KneeNoteDatabase::class.java,
            "knee_note.db",
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideKneeNoteDao(db: KneeNoteDatabase): KneeNoteDao {
        return db.kneeNoteDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun bindKneeRecordRepository(impl: LocalFirestoreKneeRecordRepository): FirestoreKneeRecordRepository

    @Binds
    @Singleton
    abstract fun bindKneeNoteRepository(impl: LocalKneeNoteRepository): KneeNoteRepository
}
