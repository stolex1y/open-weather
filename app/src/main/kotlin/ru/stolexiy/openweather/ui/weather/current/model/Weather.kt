package ru.stolexiy.openweather.ui.weather.current.model

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails

data class Weather(
    val mainInfo: MainInfo,
    val location: Location,
)

fun DomainWeatherDetails.toWeather(context: Context): Weather {
    return Weather(
        mainInfo = this.toMainInfo(context),
        location = location.toLocation(),
    )
}
