package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.database.dto.WorkDto
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal
import ua.com.vkash.harvesting.core.database.model.WorkLocal
import ua.com.vkash.harvesting.core.database.model.WorkTypeLocal

@Dao
interface WorkDao {

    @Insert
    suspend fun insertWorkTypes(list: List<WorkTypeLocal>)

    @Query("delete from work_types")
    suspend fun deleteWorkTypes()

    @Transaction
    suspend fun insertAll(list: List<WorkTypeLocal>) {
        deleteWorkTypes()
        if (list.isNotEmpty()) insertWorkTypes(list)
    }

    @Query("select rowid,* from work_types order by name")
    fun getWorkTypes(): Flow<List<WorkTypeLocal>>

    @Transaction
    @Query("select *, rowid from works where state =:state")
    suspend fun getWork(state: EntityStateLocal): List<WorkDto>

    @Query("update works set state=:state where rowid=:id")
    suspend fun setState(state: EntityStateLocal, id: Int)

    @Query("select * from works")
    fun getWorks(): Flow<List<WorkLocal>>

    @Insert
    suspend fun insertWork(work: WorkLocal): Long

    @Transaction
    @Query("select *, rowid from works where rowid =:id limit 1")
    fun getWork(id: Int): Flow<WorkDto>

    @Query("update works set brigadier_id=:workerId where rowid=:id")
    suspend fun updateBrigadier(workerId: Int, id: Int)

    @Query("update works set qty=:qty where rowid=:id")
    suspend fun updateWorkQty(qty: Double, id: Int)

    @Query("update works set square_id=:squareId where rowid=:id")
    suspend fun updateSquare(squareId: Int, id: Int)

    @Query("update works set work_type_id=:workTypeId where rowid=:id")
    suspend fun updateWorkType(workTypeId: Int, id: Int)

    @Query("delete from works where state=:state")
    suspend fun deleteSentWorks(state: EntityStateLocal = EntityStateLocal.SENT)
}
