package ua.com.vkash.harvesting.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.com.vkash.harvesting.core.database.dao.BrigadeDao
import ua.com.vkash.harvesting.core.database.dao.FieldDao
import ua.com.vkash.harvesting.core.database.dao.HarvestDao
import ua.com.vkash.harvesting.core.database.dao.SkuDao
import ua.com.vkash.harvesting.core.database.dao.TimeSheetDao
import ua.com.vkash.harvesting.core.database.dao.WorkDao
import ua.com.vkash.harvesting.core.database.dao.WorkerDao
import ua.com.vkash.harvesting.core.database.dto.BrigadeItemDetail
import ua.com.vkash.harvesting.core.database.model.BrigadeItemLocal
import ua.com.vkash.harvesting.core.database.model.BrigadeLocal
import ua.com.vkash.harvesting.core.database.model.FieldLocal
import ua.com.vkash.harvesting.core.database.model.HarvestLocal
import ua.com.vkash.harvesting.core.database.model.SkuLocal
import ua.com.vkash.harvesting.core.database.model.SquareLocal
import ua.com.vkash.harvesting.core.database.model.TimeSheetLocal
import ua.com.vkash.harvesting.core.database.model.WorkLocal
import ua.com.vkash.harvesting.core.database.model.WorkTypeLocal
import ua.com.vkash.harvesting.core.database.model.WorkerLocal

@Database(
    entities = [
        SkuLocal::class,
        FieldLocal::class,
        WorkerLocal::class,
        SquareLocal::class,
        BrigadeLocal::class,
        BrigadeItemLocal::class,
        HarvestLocal::class,
        WorkTypeLocal::class,
        WorkLocal::class,
        TimeSheetLocal::class
    ],
    views = [BrigadeItemDetail::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun skuDao(): SkuDao
    abstract fun fieldDao(): FieldDao
    abstract fun workerDao(): WorkerDao
    abstract fun brigadeDao(): BrigadeDao
    abstract fun harvestDao(): HarvestDao
    abstract fun workDao(): WorkDao
    abstract fun timeSheetDao(): TimeSheetDao
}
