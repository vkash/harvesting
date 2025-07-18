package ua.com.vkash.harvesting.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.com.vkash.harvesting.core.database.model.FieldLocal
import ua.com.vkash.harvesting.core.database.model.SquareLocal

@Dao
interface FieldDao {

    @Insert
    suspend fun insertField(field: FieldLocal): Long

    @Insert
    suspend fun insertSquares(list: List<SquareLocal>)

    @Query("delete from fields")
    suspend fun deleteFieldsAndSquares()

    @Query("select *, rowid from fields order by name")
    fun getFields(): Flow<List<FieldLocal>>

    @Query("select *, rowid from squares where field_id=:fieldId")
    fun getSquares(fieldId: Int): Flow<List<SquareLocal>>

    @Query("select *, rowid from squares where rowid=:id")
    suspend fun getSquare(id: Int): SquareLocal?

    @Query("select *, rowid from fields where rowid=:id")
    suspend fun getField(id: Int): FieldLocal?
}
