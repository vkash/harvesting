package ua.com.vkash.harvesting.core.sync.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.com.vkash.harvesting.core.sync.DownloadWorkerSyncManager
import ua.com.vkash.harvesting.core.sync.SyncManager
import ua.com.vkash.harvesting.core.sync.UpgradeWorkerSyncManager
import ua.com.vkash.harvesting.core.sync.UploadWorkerSyncManager

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

    @Binds
    @Singleton
    abstract fun bindDownloadWorkerSyncManager(impl: DownloadWorkerSyncManager): SyncManager

    @Binds
    @Singleton
    abstract fun bindUploadWorkerSyncManager(impl: UploadWorkerSyncManager): SyncManager

    @Binds
    @Singleton
    abstract fun bindUpgradeWorkerSyncManager(impl: UpgradeWorkerSyncManager): SyncManager
}
