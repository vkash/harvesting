package ua.com.vkash.harvesting.core.sync.workers

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.vkash.harvesting.core.common.ApiResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import ua.com.vkash.harvesting.core.domain.UpdateListener
import ua.com.vkash.harvesting.core.domain.UpdateRepository
import ua.com.vkash.harvesting.core.sync.KEY_MESSAGE
import ua.com.vkash.harvesting.core.sync.KEY_PROGRESS
import ua.com.vkash.harvesting.core.sync.R

@HiltWorker
class UpgradeWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateRepository: UpdateRepository
) : CoroutineWorker(appContext, workerParams) {

    private val packageInstaller = appContext.packageManager.packageInstaller
    private val installIntent = Intent().apply {
        component = ComponentName(appContext, "ua.com.vkash.harvesting.ui.main.MainActivity")
        action = PACKAGE_INSTALLED_ACTION
    }
    private val pendingIntent = PendingIntent.getActivity(
        appContext,
        0,
        installIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )

    override suspend fun doWork(): Result {
        when (val result = updateRepository.fetchUpdate(progressListener)) {
            is ApiResult.Success -> {
                startPackageInstaller(result.data)
            }

            else -> return Result.failure(result.asData())
        }

        return Result.success()
    }

    private fun <T> ApiResult<T>.asData(): Data {
        val message = when (this) {
            is ApiResult.Success -> ""
            is ApiResult.ServerError -> error.message ?: "Server error"
            is ApiResult.NetworkError -> error.message ?: "Network error"
            is ApiResult.Unauthorised -> applicationContext.getString(R.string.auth_error)
            is ApiResult.UnknownError -> applicationContext.getString(R.string.unknown_error)
            is ApiResult.OutdatedApp -> applicationContext.getString(R.string.outdated_app)
        }

        return Data.Builder().putString(KEY_MESSAGE, message).build()
    }

    private val progressListener = object : UpdateListener {
        override suspend fun onProgress(bytesSentTotal: Long, contentLength: Long?) {
            updateProgress(bytesSentTotal, contentLength)
        }
    }

    private fun startPackageInstaller(apkFile: File) {
        try {
            val params = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL
            )

            val sessionId = packageInstaller.createSession(params)
            packageInstaller.openSession(sessionId).use { session ->
                session.openWrite("package", 0, apkFile.length()).use { packageInSession ->
                    FileInputStream(apkFile).use { fis ->
                        val buffer = ByteArray(16384)
                        var bytesRead: Int
                        while (fis.read(buffer).also { bytesRead = it } != -1) {
                            packageInSession.write(buffer, 0, bytesRead)
                        }
                    }
                }
                session.commit(pendingIntent.intentSender)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: RuntimeException) {
            throw e
        } finally {
            apkFile.delete()
        }
    }

    private suspend fun updateProgress(bytesRead: Long, bytesSize: Long?) {
        val loadingStr = applicationContext.getString(
            R.string.loading_progess_kb,
            bytesRead.div(1024),
            bytesSize?.div(1024) ?: 0L
        )
        val percent = if (bytesSize != null && bytesSize > 0L) {
            bytesRead / bytesSize.toFloat()
        } else {
            0f
        }
        setProgress(
            Data.Builder()
                .putFloat(KEY_PROGRESS, percent)
                .putString(KEY_MESSAGE, loadingStr)
                .build()
        )
    }

    companion object Companion {
        const val WORK_NAME = "Update-WorkerLocal"
        private const val PACKAGE_INSTALLED_ACTION =
            "ua.com.vkash.harvesting.SESSION_API_PACKAGE_INSTALLED"
    }
}
