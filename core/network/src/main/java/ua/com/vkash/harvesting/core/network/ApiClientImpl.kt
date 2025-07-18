package ua.com.vkash.harvesting.core.network

import android.util.Base64
import ua.com.vkash.harvesting.core.common.ApiResult
import ua.com.vkash.harvesting.core.common.AppInfo
import ua.com.vkash.harvesting.core.common.di.ApplicationInfo
import ua.com.vkash.harvesting.core.common.di.DeviceId
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.compression.compress
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.PreconditionFailed
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import ua.com.vkash.harvesting.core.network.model.BrigadeRemote
import ua.com.vkash.harvesting.core.network.model.FieldRemote
import ua.com.vkash.harvesting.core.network.model.HarvestRemote
import ua.com.vkash.harvesting.core.network.model.SkuRemote
import ua.com.vkash.harvesting.core.network.model.TimeSheetRemote
import ua.com.vkash.harvesting.core.network.model.UserRemote
import ua.com.vkash.harvesting.core.network.model.WorkRemote
import ua.com.vkash.harvesting.core.network.model.WorkTypeRemote
import ua.com.vkash.harvesting.core.network.model.WorkerRemote
import java.io.File
import java.io.IOException
import java.lang.AutoCloseable
import javax.inject.Inject

class ApiClientImpl @Inject constructor(
    @ApplicationInfo appInfo: AppInfo,
    @DeviceId deviceId: String
) : ApiClient, AutoCloseable {
    private val httpClient = HttpClient(CIO) {
        engine {
            requestTimeout = 60_000L
        }
        install(ContentNegotiation) {
            json()
        }
        ContentEncoding {
            gzip()
            deflate()
        }
        if (BuildConfig.DEBUG) {
            Logging {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }
        defaultRequest {
            url(BuildConfig.API_URL)
            basicAuth("app", "123456")
            header("ver", appInfo.versionCode.toString())
            header("idtsd", deviceId)
        }
    }

    override suspend fun getUser(barcode: String): ApiResult<UserRemote> {
        val response = try {
            httpClient.get("user") {
                header("barcode", barcode)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(response.body())

        return response.handleError()
    }

    override suspend fun getSku(barcode: String): ApiResult<List<SkuRemote>> {
        val response = try {
            httpClient.get("sku") {
                header("barcode", barcode)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(response.body())

        return response.handleError()
    }

    override suspend fun getFields(barcode: String): ApiResult<List<FieldRemote>> {
        val response = try {
            httpClient.get("fields") {
                header("barcode", barcode)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(response.body())

        return response.handleError()
    }

    override suspend fun getWorkers(barcode: String): ApiResult<List<WorkerRemote>> {
        val response = try {
            httpClient.get("workers") {
                header("barcode", barcode)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(response.body())

        return response.handleError()
    }

    override suspend fun getWorkTypes(barcode: String): ApiResult<List<WorkTypeRemote>> {
        val response = try {
            httpClient.get("work_types") {
                header("barcode", barcode)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(response.body())

        return response.handleError()
    }

    override suspend fun postWork(barcode: String, workRemote: WorkRemote): ApiResult<Unit> {
        val response = try {
            httpClient.post("work_done") {
                contentType(ContentType.Application.Json)
                header("barcode", barcode)
                compress("gzip")
                setBody(workRemote)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(Unit)

        return response.handleError()
    }

    override suspend fun postBrigade(
        barcode: String,
        brigadeRemote: BrigadeRemote
    ): ApiResult<Unit> {
        val response = try {
            httpClient.post("brigade") {
                contentType(ContentType.Application.Json)
                header("barcode", barcode)
                compress("gzip")
                setBody(brigadeRemote)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(Unit)

        return response.handleError()
    }

    override suspend fun postHarvest(
        barcode: String,
        harvestRemote: HarvestRemote
    ): ApiResult<Unit> {
        val response = try {
            httpClient.post("acceptions") {
                contentType(ContentType.Application.Json)
                header("barcode", barcode)
                compress("gzip")
                setBody(harvestRemote)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(Unit)

        return response.handleError()
    }

    override suspend fun postTimeSheets(
        barcode: String,
        data: List<TimeSheetRemote>
    ): ApiResult<Unit> {
        val response = try {
            httpClient.post("time_sheet") {
                contentType(ContentType.Application.Json)
                header("barcode", barcode)
                compress("gzip")
                setBody(data)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) return ApiResult.Success(Unit)

        return response.handleError()
    }

    override suspend fun getReport(
        barcode: String,
        id: String,
        workerBarcode: String
    ): ApiResult<String> {
        val response = try {
            httpClient.get("report") {
                header("barcode", barcode)
                parameter("id", id)
                parameter("user_barcode", workerBarcode)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) {
            val encoded = Base64.encodeToString(response.bodyAsBytes(), Base64.DEFAULT)
            return ApiResult.Success(encoded)
        }

        return response.handleError()
    }

    override suspend fun getUpdate(listener: suspend (Long, Long?) -> Unit): ApiResult<File> {
        val response = try {
            httpClient.get("update") {
                header("barcode", "1234567890123")
                onDownload(listener)
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(IOException(e))
        }

        if (response.status.isSuccess()) {
            val file = File.createTempFile("update", null)
            file.outputStream().use { stream ->
                stream.write(response.bodyAsBytes())
            }
            return ApiResult.Success(file)
        }

        return response.handleError()
    }

    override suspend fun checkUpdate(): Boolean {
        val response = try {
            httpClient.get("update") {
                header("barcode", "1234567890123")
            }
        } catch (_: Exception) {
            return false
        }

        return response.status.isSuccess()
    }

    private suspend fun HttpResponse.handleError() = when (status) {
        Unauthorized -> ApiResult.Unauthorised
        PreconditionFailed -> ApiResult.OutdatedApp
        InternalServerError -> ApiResult.ServerError(
            Exception(bodyAsText())
        )

        else -> ApiResult.UnknownError
    }

    override fun close() {
        httpClient.close()
    }
}
