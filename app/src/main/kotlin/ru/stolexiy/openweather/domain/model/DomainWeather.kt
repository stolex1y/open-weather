package ru.stolexiy.openweather.domain.model

import java.util.Calendar

interface DomainWeather {
    val timestamp: Calendar?
    val location: DomainLocation
    val temperature: DomainTemperature
    val pressure: Int?
    val humidity: Int?
    val clouds: Int?
    val precipitation: DomainPrecipitation
    val wind: DomainWind
    val visibility: Int?
    val unitsOfMeasure: UnitsOfMeasure
}
