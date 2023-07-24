package ru.stolexiy.openweather.common

import java.util.Calendar

object DateUtils {
    @JvmStatic
    fun Long.toCalendar(): Calendar =
        Calendar.getInstance().apply { timeInMillis = this@toCalendar }

    @JvmStatic
    fun Long.toCalendarFromSeconds(): Calendar =
        Calendar.getInstance().apply { timeInMillis = this@toCalendarFromSeconds * 1000L }

    fun calendarFromTime(h: Int, m: Int): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = 0
            add(Calendar.HOUR, h)
            add(Calendar.MINUTE, m)
        }
    }
}
