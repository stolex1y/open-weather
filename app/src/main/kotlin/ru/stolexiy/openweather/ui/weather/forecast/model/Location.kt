package ru.stolexiy.openweather.ui.weather.forecast.model

import androidx.compose.runtime.Stable
import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.ui.util.Formatters.formatAsCoordinates

@Stable
class Location(
    latitude: Double,
    longitude: Double,
    val city: String?,
) {
    val current: String = city ?: (latitude to longitude).formatAsCoordinates()
}

fun DomainLocation.toLocation() = Location(
    latitude = latitude,
    longitude = longitude,
    city = city,
)
