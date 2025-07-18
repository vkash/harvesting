package ua.com.vkash.harvesting.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.SettingsRepository

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SettingsRepository {
    private val keyUserType = intPreferencesKey("user_type")
    private val keyUserName = stringPreferencesKey("user_name")
    private val keyUserBarcode = stringPreferencesKey("user_barcode")
    private val keyFieldId = intPreferencesKey("field_id")
    private val keySquareId = intPreferencesKey("square_id")
    private val keySkuId = intPreferencesKey("sku_id")
    private val keyTaraId = intPreferencesKey("tara_id")

    override fun userTypeFlow(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyUserType] ?: -1
    }

    override suspend fun getUserBarcode(): String = withContext(dispatcher) {
        dataStore.data.map { preferences ->
            preferences[keyUserBarcode].orEmpty()
        }.first()
    }

    override suspend fun setField(id: Int) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[keyFieldId] = id
                preferences[keySquareId] = 0
                preferences[keySkuId] = 0
                preferences[keyTaraId] = 0
            }
        }
    }

    override suspend fun getField(): Int = withContext(dispatcher) {
        dataStore.data.map { preferences ->
            preferences[keyFieldId] ?: 0
        }.first()
    }

    override fun fieldFlow(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[keyFieldId] ?: 0
    }

    override suspend fun setUser(name: String, type: Int, barcode: String) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[keyUserName] = name
                preferences[keyUserType] = type
                preferences[keyUserBarcode] = barcode
            }
        }
    }

    override fun userNameFlow(): Flow<String> = dataStore.data.map { preferences ->
        preferences[keyUserName].orEmpty()
    }

    override suspend fun setSquare(id: Int) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[keySquareId] = id
            }
        }
    }

    override suspend fun getSquare(): Int = withContext(dispatcher) {
        dataStore.data.map { preferences ->
            preferences[keySquareId] ?: 0
        }.first()
    }

    override suspend fun setTara(id: Int) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[keyTaraId] = id
            }
        }
    }

    override suspend fun getTara(): Int = withContext(dispatcher) {
        dataStore.data.map { preferences ->
            preferences[keyTaraId] ?: 0
        }.first()
    }

    override suspend fun setSku(id: Int) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[keySkuId] = id
            }
        }
    }

    override suspend fun getSku(): Int = withContext(dispatcher) {
        dataStore.data.map { preferences ->
            preferences[keySkuId] ?: 0
        }.first()
    }

    override suspend fun setDefaults() {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[keyFieldId] = 0
                preferences[keySquareId] = 0
                preferences[keySkuId] = 0
                preferences[keyTaraId] = 0
            }
        }
    }
}
