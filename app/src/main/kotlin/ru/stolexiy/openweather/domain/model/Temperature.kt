package ru.stolexiy.openweather.domain.model

data class Temperature(
    val value: Double?,
    val feelsLike: Double?,
    val min: Double?,
    val max: Double?,
    val unitsOfTempMeasure: UnitsOfTempMeasure = UnitsOfTempMeasure.CELSIUS
)
