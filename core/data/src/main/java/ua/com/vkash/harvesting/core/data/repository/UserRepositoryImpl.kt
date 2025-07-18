package ua.com.vkash.harvesting.core.data.repository

import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.di.IoDispatcher
import ua.com.vkash.harvesting.core.common.map
import dagger.Lazy
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.com.vkash.harvesting.core.domain.UserRepository
import ua.com.vkash.harvesting.core.model.data.User
import ua.com.vkash.harvesting.core.model.data.UserType
import ua.com.vkash.harvesting.core.network.ApiClient

class UserRepositoryImpl @Inject constructor(
    private val apiClient: Lazy<ApiClient>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    UserRepository {
    override suspend fun fetchUser(
        barcode: String
    ): ApiResult<User> = withContext(dispatcher) {
        apiClient.get().getUser(barcode).map {
            User(
                guid = it.guid,
                name = it.name,
                barcode = barcode,
                type = UserType.by(it.type)
            )
        }
    }
}
