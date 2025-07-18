package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ua.com.vkash.harvesting.core.database.model.SkuLocal

@Dao
interface SkuDao {

    @Insert
    suspend fun insert(list: List<SkuLocal>)

    @Query("delete from sku")
    suspend fun deleteSku()

    @Transaction
    suspend fun insertAll(list: List<SkuLocal>) {
        deleteSku()
        if (list.isNotEmpty()) insert(list)
    }

    @Query("select *, rowid from sku where type=1 order by type, name")
    suspend fun getTara(): List<SkuLocal>

    @Query("select *, rowid from sku order by type, name")
    suspend fun getSku(): List<SkuLocal>

    @Query(
        "select *, rowid from sku where rowid in (select sku_id from squares where field_id=:fieldId) order by type, name"
    )
    suspend fun getSku(fieldId: Int): List<SkuLocal>
}
