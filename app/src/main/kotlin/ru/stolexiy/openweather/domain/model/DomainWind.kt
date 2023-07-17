package ru.stolexiy.openweather.domain.model

data class DomainWind(
    val speed: Double?,
    val deg: Int?,
    val gust: Double?,
    val unitsOfMeasure: UnitsOfWindMeasure = UnitsOfWindMeasure.METERS_PER_SECOND
)
