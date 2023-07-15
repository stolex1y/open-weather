package ru.stolexiy.openweather.ui.util

import android.content.Context
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.domain.model.UnitsOfTempMeasure
import ru.stolexiy.openweather.domain.model.UnitsOfWindMeasure

object Formatters {
    private const val TEMP_FORMAT = "%02.1f%s"
    private const val WIND_SPEED_FORMAT = "%02.1f %s"
    fun Double.formatAsTemperature(units: UnitsOfTempMeasure): String {
        return TEMP_FORMAT.format(this, units.sign)
    }

    fun Int.formatAsPercents(): String {
        return "$this%"
    }

    fun Int.formatAsPressure(context: Context): String {
        return "$this ${context.getString(R.string.hectopascal)}"
    }

    fun Double.formatAsWindSpeed(windMeasure: UnitsOfWindMeasure, context: Context): String {
        return WIND_SPEED_FORMAT.format(this, context.getString(windMeasure.sign))
    }
}
