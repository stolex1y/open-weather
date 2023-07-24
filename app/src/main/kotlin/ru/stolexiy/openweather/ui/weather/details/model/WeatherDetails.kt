package ru.stolexiy.openweather.ui.weather.details.model

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails

data class WeatherDetails(
    val mainInfo: MainInfo,
    val temp: Temperature,
    val wind: Wind,
    val location: Location,
    val precipitation: Precipitation,
)

fun DomainWeatherDetails.toWeatherDetails(context: Context): WeatherDetails {
    return WeatherDetails(
        mainInfo = this.toMainInfo(context),
        temp = temperature.toTemperature(),
        wind = wind.toWind(context),
        location = location.toLocation(),
        precipitation = precipitation.toPrecipitation(context),
    )
}