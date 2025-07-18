package ua.com.vkash.harvesting.core.model.data

import java.util.Date

data class TimeSheetDetail(
    val id: Int,
    val workerId: Int,
    val workerGuid: String,
    val fieldId: Int,
    val fieldGuid: String,
    val eventTime: Date,
    val eventStart: Boolean,
    val workerName: String,
    val fieldName: String
)
