package ua.com.vkash.harvesting.core.domain

import ua.com.vkash.harvesting.core.common.ApiResult
import java.io.File

interface UpdateRepository {
    suspend fun fetchUpdate(listener: UpdateListener): ApiResult<File>

    suspend fun checkUpdate(): Boolean
}

interface UpdateListener {
    suspend fun onProgress(bytesSentTotal: Long, contentLength: Long?)
}
