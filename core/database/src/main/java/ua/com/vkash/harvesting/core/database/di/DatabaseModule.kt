package ua.com.vkash.harvesting.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import ua.com.vkash.harvesting.core.database.AppDatabase
import ua.com.vkash.harvesting.core.database.dao.BrigadeDao
import ua.com.vkash.harvesting.core.database.dao.FieldDao
import ua.com.vkash.harvesting.core.database.dao.HarvestDao
import ua.com.vkash.harvesting.core.database.dao.SkuDao
import ua.com.vkash.harvesting.core.database.dao.TimeSheetDao
import ua.com.vkash.harvesting.core.database.dao.WorkDao
import ua.com.vkash.harvesting.core.database.dao.WorkerDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app.db"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    @Provides
    fun provideSkuDao(appDatabase: AppDatabase): SkuDao = appDatabase.skuDao()

    @Provides
    fun provideFieldDao(appDatabase: AppDatabase): FieldDao = appDatabase.fieldDao()

    @Provides
    fun provideWorkerDao(appDatabase: AppDatabase): WorkerDao = appDatabase.workerDao()

    @Provides
    fun provideBrigadeDao(appDatabase: AppDatabase): BrigadeDao = appDatabase.brigadeDao()

    @Provides
    fun provideHarvestDao(appDatabase: AppDatabase): HarvestDao = appDatabase.harvestDao()

    @Provides
    fun provideWorkDao(appDatabase: AppDatabase): WorkDao = appDatabase.workDao()

    @Provides
    fun provideTimeSheetDao(appDatabase: AppDatabase): TimeSheetDao = appDatabase.timeSheetDao()
}
