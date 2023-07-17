package ru.stolexiy.openweather.ui.weather.current

import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.ui.util.Formatters
import ru.stolexiy.openweather.ui.util.Formatters.formatAsCoordinates
import ru.stolexiy.openweather.ui.util.Formatters.toString
import java.util.Calendar

data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: String?,
    val sunrise: Calendar,
    val sunset: Calendar,
) {
    val coordinatesStr: String = (latitude to longitude).formatAsCoordinates()
    val sunriseStr: String = sunrise.toString(Formatters.HM_TIME)
    val sunsetStr: String = sunset.toString(Formatters.HM_TIME)
}

fun DomainLocation.toLocation() = Location(
    latitude = latitude,
    longitude = longitude,
    city = city,
    sunrise = sunrise,
    sunset = sunset,
)
