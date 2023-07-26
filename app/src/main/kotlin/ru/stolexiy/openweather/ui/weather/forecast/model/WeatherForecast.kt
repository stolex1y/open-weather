package ru.stolexiy.openweather.ui.weather.forecast.model

import ru.stolexiy.openweather.domain.model.DomainWeatherForecast

data class WeatherForecast(
    val mainInfo: MainInfo,
    val location: Location,
    val details: Details,
)

fun DomainWeatherForecast.toWeatherForecast() = WeatherForecast(
    mainInfo = toMainInfo(),
    location = location.toLocation(),
    details = toDetails()
)
