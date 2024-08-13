import com.github.yumayuma708.apps.model.KneeRecord
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface FirestoreKneeRecordRepository {
    suspend fun create(
        dateTime: LocalDateTime,
        isRight: Boolean,
        pain: Float,
        weather: String,
        note: String,
    ): KneeRecord

    suspend fun getById(id: String): KneeRecord?

    suspend fun getAll(): Flow<List<KneeRecord>>

    suspend fun update(record: KneeRecord)

    suspend fun delete(recordId: String)
}
