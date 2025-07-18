package ua.com.vkash.harvesting.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.com.vkash.harvesting.core.data.repository.BrigadeRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.FieldRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.HarvestRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.ReportRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.SettingsRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.SkuRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.TimeSheetRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.UpdateRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.UserRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.WorkRepositoryImpl
import ua.com.vkash.harvesting.core.data.repository.WorkerRepositoryImpl
import ua.com.vkash.harvesting.core.domain.BrigadeRepository
import ua.com.vkash.harvesting.core.domain.FieldRepository
import ua.com.vkash.harvesting.core.domain.HarvestRepository
import ua.com.vkash.harvesting.core.domain.ReportRepository
import ua.com.vkash.harvesting.core.domain.SettingsRepository
import ua.com.vkash.harvesting.core.domain.SkuRepository
import ua.com.vkash.harvesting.core.domain.TimeSheetRepository
import ua.com.vkash.harvesting.core.domain.UpdateRepository
import ua.com.vkash.harvesting.core.domain.UserRepository
import ua.com.vkash.harvesting.core.domain.WorkRepository
import ua.com.vkash.harvesting.core.domain.WorkerRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindSkuRepository(impl: SkuRepositoryImpl): SkuRepository

    @Binds
    @Singleton
    abstract fun bindWorkerRepository(impl: WorkerRepositoryImpl): WorkerRepository

    @Binds
    @Singleton
    abstract fun bindReportRepository(impl: ReportRepositoryImpl): ReportRepository

    @Binds
    @Singleton
    abstract fun bindUpdateRepository(impl: UpdateRepositoryImpl): UpdateRepository

    @Binds
    @Singleton
    abstract fun bindTimeSheetRepository(impl: TimeSheetRepositoryImpl): TimeSheetRepository

    @Binds
    @Singleton
    abstract fun bindWorkRepository(impl: WorkRepositoryImpl): WorkRepository

    @Binds
    @Singleton
    abstract fun bindHarvestRepository(impl: HarvestRepositoryImpl): HarvestRepository

    @Binds
    @Singleton
    abstract fun bindBrigadeRepository(impl: BrigadeRepositoryImpl): BrigadeRepository

    @Binds
    @Singleton
    abstract fun bindFieldRepository(impl: FieldRepositoryImpl): FieldRepository
}
