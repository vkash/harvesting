package ua.com.vkash.harvesting.core.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun userTypeFlow(): Flow<Int>

    suspend fun getUserBarcode(): String

    suspend fun setField(id: Int)

    suspend fun getField(): Int

    fun fieldFlow(): Flow<Int>

    suspend fun setUser(name: String, type: Int, barcode: String)

    fun userNameFlow(): Flow<String>

    suspend fun setSquare(id: Int)

    suspend fun getSquare(): Int

    suspend fun setTara(id: Int)

    suspend fun getTara(): Int

    suspend fun setSku(id: Int)

    suspend fun getSku(): Int

    suspend fun setDefaults()
}
