package ru.stolexiy.openweather.ui.weather.details.model

import ru.stolexiy.openweather.domain.model.DomainTemperature
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature

data class Temperature(
    val feelsLike: String?,
    val min: String?,
    val max: String?,
)

fun DomainTemperature.toTemperature(): Temperature {
    return Temperature(
        feelsLike = feelsLike.formatAsTemperature(unitsOfTempMeasure),
        min = min.formatAsTemperature(unitsOfTempMeasure),
        max = max.formatAsTemperature(unitsOfTempMeasure)
    )
}
