package ua.com.vkash.harvesting.core.common

import java.io.IOException

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class NetworkError(val error: IOException) : ApiResult<Nothing>
    data class ServerError(val error: Throwable) : ApiResult<Nothing>
    data object OutdatedApp : ApiResult<Nothing>
    data object Unauthorised : ApiResult<Nothing>
    data object UnknownError : ApiResult<Nothing>
}

inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Success -> ApiResult.Success(transform(data))
    is ApiResult.NetworkError -> ApiResult.NetworkError(error)
    is ApiResult.ServerError -> ApiResult.ServerError(error)
    ApiResult.OutdatedApp -> ApiResult.OutdatedApp
    ApiResult.Unauthorised -> ApiResult.Unauthorised
    ApiResult.UnknownError -> ApiResult.UnknownError
}
