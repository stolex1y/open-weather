package ru.stolexiy.openweather.ui.weather.forecast.model

import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import ru.stolexiy.openweather.domain.model.UnitsOfDistanceMeasure
import ru.stolexiy.openweather.domain.model.UnitsOfWindMeasure

data class Details(
    val windSpeed: Double?,
    val windDeg: Int?,
    val unitsOfWindMeasure: UnitsOfWindMeasure,
    val humidity: Int,
    val visibility: Int,
    val unitsOfDistanceMeasure: UnitsOfDistanceMeasure,
    val pressure: Int,
    val precipitationProb: Double,
)

fun DomainWeatherForecast.toDetails() = Details(
    windSpeed = wind.speed,
    windDeg = wind.deg,
    unitsOfWindMeasure = unitsOfMeasure.windMeasure,
    humidity = humidity,
    visibility = visibility,
    unitsOfDistanceMeasure = unitsOfMeasure.distanceMeasure,
    pressure = pressure,
    precipitationProb = precipitationProb
)
