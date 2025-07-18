package ua.com.vkash.harvesting.core.domain

import com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.model.data.User

interface UserRepository {
    suspend fun fetchUser(barcode: String): ApiResult<User>
}
