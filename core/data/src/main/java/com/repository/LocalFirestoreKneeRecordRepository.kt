package com.repository

import FirestoreKneeRecordRepository
import com.github.yumayuma708.apps.model.KneeRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

class LocalFirestoreKneeRecordRepository
    @Inject
    constructor() : FirestoreKneeRecordRepository {
        private val db = FirebaseFirestore.getInstance()
        private val collection = db.collection("knee_records")

        override suspend fun create(
            dateTime: LocalDateTime,
            isRight: Boolean,
            pain: Float,
            weather: String,
            note: String,
        ): KneeRecord {
            val newRecord =
                KneeRecord(
                    id = "",
                    dateTime = dateTime,
                    isRight = isRight,
                    pain = pain,
                    weather = weather,
                    note = note,
                )
            val documentRef = collection.document()
            documentRef.set(newRecord).await()
            return newRecord
        }

        override suspend fun getById(id: String): KneeRecord? {
            val document = collection.document(id).get().await()
            return document.toObject(KneeRecord::class.java)
        }

        override suspend fun getAll(): Flow<List<KneeRecord>> {
            return flow {
                val snapshot = collection.get().await()
                val records =
                    snapshot.documents.map { document ->
                        document.toObject(KneeRecord::class.java)!!
                    }
                emit(records)
            }
        }

        override suspend fun update(record: KneeRecord) {
            val documentRef = collection.document()
            documentRef.set(record).await()
        }

        override suspend fun delete(recordId: String) {
            val documentRef = collection.document(recordId)
            documentRef.delete().await()
        }
    }
