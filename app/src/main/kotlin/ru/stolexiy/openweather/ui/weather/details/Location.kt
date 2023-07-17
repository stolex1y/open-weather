package ru.stolexiy.openweather.ui.weather.details

import ru.stolexiy.openweather.domain.model.DomainLocation

data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: String?,
)

fun DomainLocation.toLocation() = Location(
    latitude = latitude,
    longitude = longitude,
    city = city,
)
