package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.database.dto.HarvestDto
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal
import ua.com.vkash.harvesting.core.database.model.HarvestLocal

@Dao
interface HarvestDao {

    @Query("select * from harvests")
    fun getHarvests(): Flow<List<HarvestLocal>>

    @Insert
    suspend fun insertHarvest(harvest: HarvestLocal): Long

    @Query("delete from harvests where state=:state")
    suspend fun deleteSent(state: EntityStateLocal = EntityStateLocal.SENT)

    @Transaction
    @Query("select *, rowid from harvests where rowid =:id limit 1")
    fun getHarvest(id: Int): Flow<HarvestDto>

    @Transaction
    @Query("select *, rowid from harvests where state =:state")
    suspend fun getHarvests(state: EntityStateLocal): List<HarvestDto>

    @Query("update harvests set state=:state where rowid=:harvestId")
    suspend fun setState(state: EntityStateLocal, harvestId: Int)

    @Query("update harvests set worker_id=:workerId where rowid=:harvestId")
    suspend fun updateWorker(workerId: Int, harvestId: Int)

    @Query("update harvests set tara_id=:skuId where rowid=:harvestId")
    suspend fun updateTara(skuId: Int, harvestId: Int)

    @Query("update harvests set weight=:weight where rowid=:harvestId")
    suspend fun updateWeight(weight: Double, harvestId: Int)

    @Query("update harvests set tara_qty=:qty where rowid=:harvestId")
    suspend fun updateTaraQty(qty: Int, harvestId: Int)

    @Query("update harvests set square_id=:squareId, sku_id=:skuId where rowid=:harvestId")
    suspend fun updateSquareSku(squareId: Int, skuId: Int, harvestId: Int)
}
