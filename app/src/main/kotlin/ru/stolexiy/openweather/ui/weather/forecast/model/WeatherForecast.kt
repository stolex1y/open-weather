package ru.stolexiy.openweather.ui.weather.forecast.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import ru.stolexiy.openweather.domain.model.UnitsOfTempMeasure
import ru.stolexiy.openweather.ui.util.Formatters
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature
import ru.stolexiy.openweather.ui.util.Formatters.toString
import java.util.Calendar

@Stable
class WeatherForecast(
    @StringRes val weatherGroupLabel: Int,
    @DrawableRes val weatherGroupIcon: Int,
    val location: Location,
    temp: Double,
    unitsOfTempMeasure: UnitsOfTempMeasure,
    val timestamp: Calendar
) {
    val tempStr: String = temp.formatAsTemperature(unitsOfTempMeasure)
    val dateStr: String = timestamp.toString(Formatters.DM_DATE)
    val timeStr: String = timestamp.toString(Formatters.HM_TIME)
}

fun DomainWeatherForecast.toWeatherForecast() = WeatherForecast(
    weatherGroupLabel = weatherGroup.label,
    weatherGroupIcon = weatherGroup.icon,
    temp = temperature.value,
    unitsOfTempMeasure = unitsOfMeasure.tempMeasure,
    location = location.toLocation(),
    timestamp = timestamp
)
