package ru.stolexiy.openweather.domain.model

import java.util.Calendar

interface Weather {
    val timestamp: Calendar?
    val location: Location
    val temperature: Temperature
    val pressure: Int?
    val humidity: Int?
    val clouds: Int?
    val precipitation: Precipitation
    val wind: Wind
}
