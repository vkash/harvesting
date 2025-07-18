package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.database.dto.BrigadeDto
import ua.com.vkash.harvesting.core.database.model.BrigadeItemLocal
import ua.com.vkash.harvesting.core.database.model.BrigadeLocal
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal

@Dao
interface BrigadeDao {

    @Transaction
    @Query("select * from brigades")
    fun getBrigades(): Flow<List<BrigadeLocal>>

    @Insert
    suspend fun insertBrigade(brigade: BrigadeLocal): Long

    @Query("delete from brigades where state=:state")
    suspend fun deleteBrigadeSent(state: EntityStateLocal = EntityStateLocal.SENT)

    @Transaction
    @Query("select *, rowid from brigades where rowid =:brigadeId limit 1")
    fun getBrigade(brigadeId: Int): Flow<BrigadeDto>

    @Transaction
    @Query("select *, rowid from brigades where state =:state")
    suspend fun getBrigades(state: EntityStateLocal): List<BrigadeDto>

    @Query("update brigades set state=:state where rowid=:brigadeId")
    suspend fun setState(state: EntityStateLocal, brigadeId: Int)

    @Query("update brigades set brigadier_id=:workerId where rowid=:brigadeId")
    suspend fun updateBrigadier(workerId: Int, brigadeId: Int)

    @Insert
    suspend fun insertWorker(brigadeItem: BrigadeItemLocal)

    @Query(
        "select *, rowid from brigades_items where worker_id=:workerId and rowid=:brigadeId limit 1"
    )
    suspend fun findBrigadeWorker(workerId: Int, brigadeId: Int): BrigadeItemLocal?
}
