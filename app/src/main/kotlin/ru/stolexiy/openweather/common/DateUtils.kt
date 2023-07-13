package ru.stolexiy.openweather.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {
    const val DMY_DATE = "dd.MM.yyyy"
    const val DMY_DATETIME = "dd.MM.yyyy HH:mm"
    const val HM_TIME = "HH:mm"

    @JvmStatic
    fun Long.toCalendar(): Calendar =
        Calendar.getInstance().apply { timeInMillis = this@toCalendar }

    @JvmStatic
    fun Calendar?.toString(pattern: String): String {
        return if (this == null)
            ""
        else
            SimpleDateFormat(pattern, Locale.getDefault()).format(this)
    }
}
