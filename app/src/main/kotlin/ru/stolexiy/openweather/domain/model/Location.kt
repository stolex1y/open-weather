package ru.stolexiy.openweather.domain.model

import java.util.Calendar

data class Location(
    val latitude: Double?,
    val longitude: Double?,
    val city: String?,
    val sunrise: Calendar?,
    val sunset: Calendar?
)
