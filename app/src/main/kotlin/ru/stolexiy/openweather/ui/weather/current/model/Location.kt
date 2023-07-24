package ru.stolexiy.openweather.ui.weather.current.model

import androidx.compose.runtime.Stable
import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.ui.util.Formatters
import ru.stolexiy.openweather.ui.util.Formatters.formatAsCoordinates
import ru.stolexiy.openweather.ui.util.Formatters.toString
import java.util.Calendar

@Stable
class Location(
    latitude: Double,
    longitude: Double,
    val city: String?,
    sunrise: Calendar,
    sunset: Calendar,
) {
    val sunriseStr: String = sunrise.toString(Formatters.HM_TIME)
    val sunsetStr: String = sunset.toString(Formatters.HM_TIME)
    val current: String = city ?: (latitude to longitude).formatAsCoordinates()
}

fun DomainLocation.toLocation() = Location(
    latitude = latitude,
    longitude = longitude,
    city = city,
    sunrise = sunrise,
    sunset = sunset,
)
