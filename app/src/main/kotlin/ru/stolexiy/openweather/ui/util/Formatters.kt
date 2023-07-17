package ru.stolexiy.openweather.ui.util

import android.content.Context
import androidx.annotation.StringRes
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.domain.model.UnitsOfDistanceMeasure
import ru.stolexiy.openweather.domain.model.UnitsOfTempMeasure
import ru.stolexiy.openweather.domain.model.UnitsOfWindMeasure
import kotlin.math.roundToInt

object Formatters {
    private const val COORDINATES_FORMAT = "%03.2f° %03.2f°"

    fun Double.formatAsTemperature(units: UnitsOfTempMeasure): String {
        return "%d%s".format(this.roundToInt(), units.sign)
    }

    fun Int.formatAsPercents(): String {
        return "$this%"
    }

    fun Double.toString(afterComma: Int): String {
        return "%0${afterComma}.${afterComma}f".format(this)
    }

    fun Int.formatAsPressure(context: Context): String {
        return "$this ${context.getString(R.string.hectopascal)}"
    }

    fun Double.formatAsWindSpeed(
        windMeasure: UnitsOfWindMeasure,
        context: Context,
    ): String {
        return "%d %s".format(this.roundToInt(), context.getString(windMeasure.sign))
    }

    fun Pair<Double, Double>.formatAsCoordinates(): String {
        return COORDINATES_FORMAT.format(first, second)
    }

    fun Double.formatAsPrecipitation(context: Context): String {
        return "${this.roundToInt()} ${context.getString(R.string.mm)}"
    }

    fun Int.parseWindDirection(context: Context): String {
        return context.getString(getCardinalDirection(this))
    }

    fun Int.formatAsDistance(context: Context, units: UnitsOfDistanceMeasure): String {
        return "$this ${context.getString(units.sign)}"
    }

    @StringRes
    private fun getCardinalDirection(deg: Int): Int {
        val directions = intArrayOf(
            R.string.direction_N,
            R.string.direction_NE,
            R.string.direction_E,
            R.string.direction_SE,
            R.string.direction_S,
            R.string.direction_SW,
            R.string.direction_W,
            R.string.direction_NW
        )
        return directions[((deg % 360) / 45.0).roundToInt() % 8]
    }
}
