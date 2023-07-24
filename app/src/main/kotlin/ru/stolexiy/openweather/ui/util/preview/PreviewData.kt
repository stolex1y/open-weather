package ru.stolexiy.openweather.ui.util.preview

import ru.stolexiy.openweather.common.DateUtils.calendarFromTime
import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.domain.model.DomainPrecipitation
import ru.stolexiy.openweather.domain.model.DomainTemperature
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import ru.stolexiy.openweather.domain.model.DomainWind
import ru.stolexiy.openweather.domain.model.WeatherGroup
import ru.stolexiy.openweather.ui.util.Formatters
import ru.stolexiy.openweather.ui.util.Formatters.toString
import java.util.Calendar

object PreviewData {
    fun getFakeDomainWeatherDetails() = DomainWeatherDetails(
        location = DomainLocation(
            latitude = 26.0,
            longitude = 43.45,
            city = "Санкт-Петербург",
            sunrise = calendarFromTime(4, 23),
            sunset = calendarFromTime(22, 45)
        ),
        temperature = DomainTemperature(
            value = 25.6,
            feelsLike = 22.3,
            min = 21.0,
            max = 24.0
        ),
        pressure = 1012,
        humidity = 70,
        clouds = 90,
        precipitation = DomainPrecipitation(
            rain1h = 2.0,
            rain3h = null,
            snow1h = null,
            snow3h = null,
        ),
        wind = DomainWind(
            speed = 2.3,
            deg = 90,
            gust = null
        ),
        timestamp = Calendar.getInstance(),
        visibility = 10000,
        weatherGroup = WeatherGroup.CLOUDY
    )

    fun getFakeDomainWeatherForecasts(): List<DomainWeatherForecast> {
        val forecastTimestamp = Calendar.getInstance()
        return (1..10).map { i ->
            println(forecastTimestamp.toString(Formatters.DMY_DATETIME))
            forecastTimestamp.add(Calendar.HOUR, 3)
            DomainWeatherForecast(
                location = DomainLocation(
                    latitude = 26.0,
                    longitude = 43.45,
                    city = "Санкт-Петербург",
                    sunrise = calendarFromTime(4, 23 + i),
                    sunset = calendarFromTime(22, 45 - i)
                ),
                temperature = DomainTemperature(
                    value = 25.6 + Math.random() * 5,
                    feelsLike = 22.3 + Math.random() * 5,
                    min = 21.0,
                    max = 24.0
                ),
                pressure = 1012,
                humidity = 70,
                clouds = 90,
                precipitation = DomainPrecipitation(
                    rain1h = 2.0,
                    rain3h = null,
                    snow1h = null,
                    snow3h = null,
                ),
                wind = DomainWind(
                    speed = 2.3,
                    deg = 90,
                    gust = null
                ),
                timestamp = forecastTimestamp.clone() as Calendar,
                visibility = 10000,
                weatherGroup = WeatherGroup.values()[(Math.random() * (WeatherGroup.values().size - 1)).toInt()],
                precipitationProb = 50.0
            )
        }
    }
}
