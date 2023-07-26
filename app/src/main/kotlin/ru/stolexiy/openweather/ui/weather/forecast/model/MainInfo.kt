package ru.stolexiy.openweather.ui.weather.forecast.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import ru.stolexiy.openweather.domain.model.UnitsOfTempMeasure
import java.util.Calendar

data class MainInfo(
    @StringRes val weatherGroupLabel: Int,
    @DrawableRes val weatherGroupIcon: Int,
    val temperature: Double,
    val unitsOfTempMeasure: UnitsOfTempMeasure,
    val timestamp: Calendar
) {
//    val timestamp: Calendar = timestamp.roundedHours()
//    val tempStr: String =
//    val dateStr: String =
//    val timeStr: String =
}

fun DomainWeatherForecast.toMainInfo() = MainInfo(
    weatherGroupLabel = weatherGroup.label,
    weatherGroupIcon = weatherGroup.icon,
    temperature = temperature.value,
    unitsOfTempMeasure = unitsOfMeasure.tempMeasure,
    timestamp = timestamp.clone() as Calendar
)
