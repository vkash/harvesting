package ua.com.vkash.harvesting.feature.appmenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class MainCard(
    @DrawableRes val image: Int,
    @StringRes val title: Int
) {
    BRIGADES(R.drawable.brigades, R.string.brigades),
    HARVESTS(R.drawable.harvesting, R.string.harvesting),
    WORKS(R.drawable.work_done, R.string.completed_works),
    REPORTS(R.drawable.reports, R.string.reports),
    TIMESHEET(R.drawable.time_sheet, R.string.time_sheet)
}
