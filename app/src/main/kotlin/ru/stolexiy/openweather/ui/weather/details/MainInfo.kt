package ru.stolexiy.openweather.ui.weather.details

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.ui.util.Formatters.formatAsDistance
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPercents
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPressure

data class MainInfo(
    val humidity: String?,
    val pressure: String?,
    val clouds: String?,
    val visibility: String?,
)

fun DomainWeatherDetails.toMainInfo(context: Context): MainInfo {
    val humidityStr = humidity?.formatAsPercents()
    val cloudsStr = clouds?.formatAsPercents()
    val pressureStr = pressure?.formatAsPressure(context)
    val visibilityStr = visibility?.formatAsDistance(context, unitsOfMeasure.distanceMeasure)
    return MainInfo(
        humidity = humidityStr,
        pressure = pressureStr,
        clouds = cloudsStr,
        visibility = visibilityStr,
    )
}