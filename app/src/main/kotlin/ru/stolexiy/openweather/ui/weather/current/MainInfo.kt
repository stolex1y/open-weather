package ru.stolexiy.openweather.ui.weather.current

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.ui.util.Formatters.formatAsDistance
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPercents
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPressure
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature
import ru.stolexiy.openweather.ui.util.Formatters.formatAsWindSpeed

data class MainInfo(
    val temperature: String?,
    val humidity: String?,
    val pressure: String?,
    val visibility: String?,
    val windSpeed: String?,
    @DrawableRes val weatherGroupIcon: Int,
    @StringRes val weatherGroupLabel: Int,
)

fun DomainWeatherDetails.toMainInfo(context: Context): MainInfo {
    val tempStr = temperature.value?.formatAsTemperature(unitsOfMeasure.tempMeasure)
    val humidityStr = humidity?.formatAsPercents()
    val pressureStr = pressure?.formatAsPressure(context)
    val visibilityStr = visibility?.formatAsDistance(context, unitsOfMeasure.distanceMeasure)
    val windSpeedStr = wind.speed?.formatAsWindSpeed(wind.unitsOfMeasure, context)
    return MainInfo(
        temperature = tempStr,
        humidity = humidityStr,
        pressure = pressureStr,
        visibility = visibilityStr,
        windSpeed = windSpeedStr,
        weatherGroupIcon = weatherGroup.icon,
        weatherGroupLabel = weatherGroup.label,
    )
}