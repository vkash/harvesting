package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.database.dto.TimeSheetDto
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal
import ua.com.vkash.harvesting.core.database.model.TimeSheetLocal

@Dao
interface TimeSheetDao {
    @Query("delete from time_sheets where state=:state")
    suspend fun deleteSent(state: EntityStateLocal = EntityStateLocal.SENT)

    @Transaction
    @Query("select *, rowid from time_sheets where state =:state")
    suspend fun getUnsent(state: EntityStateLocal): List<TimeSheetDto>

    @Query("update time_sheets set state=:state where rowid in (:ids)")
    suspend fun setState(state: EntityStateLocal, ids: List<Int>)

    @Transaction
    @Query("select *, rowid from time_sheets order by rowid desc")
    fun getAll(): Flow<List<TimeSheetDto>>

    @Query("select *, rowid from time_sheets where worker_id=:workerId order by rowid desc limit 1")
    suspend fun getLast(workerId: Int): TimeSheetLocal?

    @Insert
    suspend fun insert(timeSheet: TimeSheetLocal)
}
