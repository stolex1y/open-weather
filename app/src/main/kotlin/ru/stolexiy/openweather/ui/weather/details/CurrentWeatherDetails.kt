package ru.stolexiy.openweather.ui.weather.details

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.ui.util.Formatters.formatAsDistance
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPercents
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPressure

data class CurrentWeatherDetails(
    val humidity: String?,
    val pressure: String?,
    val clouds: String?,
    val visibility: String?,
    val temp: Temperature,
    val wind: Wind,
    val location: Location,
    val precipitation: Precipitation,
)

fun DomainWeatherDetails.toCurrentWeatherDetails(context: Context): CurrentWeatherDetails {
    val humidityStr = humidity?.formatAsPercents()
    val cloudsStr = clouds?.formatAsPercents()
    val pressureStr = pressure?.formatAsPressure(context)
    val visibilityStr = visibility?.formatAsDistance(context, unitsOfMeasure.distanceMeasure)
    return CurrentWeatherDetails(
        humidity = humidityStr,
        pressure = pressureStr,
        clouds = cloudsStr,
        visibility = visibilityStr,
        temp = temperature.toTemperature(),
        wind = wind.toWind(context),
        location = location.toLocation(),
        precipitation = precipitation.toPrecipitation(context),
    )
}
