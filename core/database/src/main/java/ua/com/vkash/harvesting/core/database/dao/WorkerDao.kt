package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ua.com.vkash.harvesting.core.database.model.WorkerLocal

@Dao
interface WorkerDao {

    @Query("delete from workers")
    suspend fun deleteWorkers()

    @Insert
    suspend fun insertWorkers(list: List<WorkerLocal>)

    @Transaction
    suspend fun insertAll(workerList: List<WorkerLocal>) {
        deleteWorkers()
        if (workerList.isNotEmpty()) insertWorkers(workerList)
    }

    @Query("select *, rowid from workers where barcode like :barcode limit 1")
    suspend fun findWorker(barcode: String): WorkerLocal?
}
