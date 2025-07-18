package ua.com.vkash.harvesting.core.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import ua.com.vkash.harvesting.core.database.model.EntityStateLocal

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String): Date? = try {
        formatter.parse(value)
    } catch (_: Exception) {
        null
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): String = formatter.format(date)

    @TypeConverter
    fun toEntityState(
        value: String?
    ): EntityStateLocal? = value?.let { EntityStateLocal.valueOf(value) }

    @TypeConverter
    fun fromEntityState(state: EntityStateLocal?): String? = state?.name

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT).apply {
        timeZone = TimeZone.getDefault()
    }
}
