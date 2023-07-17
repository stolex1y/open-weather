package ru.stolexiy.openweather.ui.weather.details

import ru.stolexiy.openweather.domain.model.DomainTemperature
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature

data class Temperature(
    val value: String?,
    val feelsLike: String?,
    val min: String?,
    val max: String?,
)

fun DomainTemperature.toTemperature(): Temperature {
    return Temperature(
        value = value?.formatAsTemperature(unitsOfTempMeasure),
        feelsLike = feelsLike?.formatAsTemperature(unitsOfTempMeasure),
        min = min?.formatAsTemperature(unitsOfTempMeasure),
        max = max?.formatAsTemperature(unitsOfTempMeasure)
    )
}
