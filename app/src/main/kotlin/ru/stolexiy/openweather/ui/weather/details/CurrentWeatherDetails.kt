package ru.stolexiy.openweather.ui.weather.details

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails

data class CurrentWeatherDetails(
    val mainInfo: MainInfo,
    val temp: Temperature,
    val wind: Wind,
    val location: Location,
    val precipitation: Precipitation,
)

fun DomainWeatherDetails.toCurrentWeatherDetails(context: Context): CurrentWeatherDetails {
    return CurrentWeatherDetails(
        mainInfo = this.toMainInfo(context),
        temp = temperature.toTemperature(),
        wind = wind.toWind(context),
        location = location.toLocation(),
        precipitation = precipitation.toPrecipitation(context),
    )
}
