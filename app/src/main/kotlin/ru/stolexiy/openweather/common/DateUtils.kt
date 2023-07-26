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

    fun Calendar.roundedHours(): Calendar {
        return (this.clone() as Calendar).apply {
            val min = get(Calendar.MINUTE)
            if (min >= 30)
                add(Calendar.HOUR, 1)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}
