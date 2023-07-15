package ru.stolexiy.openweather.domain.model

import java.util.Calendar

data class WeatherForecast(
    override val location: Location,
    override val temperature: Temperature,
    override val pressure: Int?,
    override val humidity: Int?,
    override val clouds: Int?,
    override val precipitation: Precipitation,
    override val wind: Wind,
    override val timestamp: Calendar?,
    val precipitationProb: Double?,
    override val unitsOfMeasure: UnitsOfMeasure = UnitsOfMeasure.METRIC,
) : Weather
