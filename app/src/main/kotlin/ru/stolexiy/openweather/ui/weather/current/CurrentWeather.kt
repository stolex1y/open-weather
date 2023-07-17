package ru.stolexiy.openweather.ui.weather.current

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.ui.util.Formatters.DMY_DATE
import ru.stolexiy.openweather.ui.util.Formatters.HM_TIME
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPercents
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPressure
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature
import ru.stolexiy.openweather.ui.util.Formatters.formatAsWindSpeed
import ru.stolexiy.openweather.ui.util.Formatters.toString
import java.util.Calendar

data class CurrentWeather(
    val timestamp: Calendar,
    val temp: String?,
    val humidity: String?,
    val pressure: String?,
    val windSpeed: String?,
    val location: Location,
) {
    val dateStr: String = timestamp.toString(DMY_DATE)
    val timeStr: String = timestamp.toString(HM_TIME)
}

fun DomainWeatherDetails.toCurrentWeather(context: Context): CurrentWeather {
    val tempStr = temperature.value?.formatAsTemperature(temperature.unitsOfTempMeasure)
    val humidityStr = humidity?.formatAsPercents()
    val pressureStr = pressure?.formatAsPressure(context)
    val windSpeedStr = wind.speed?.formatAsWindSpeed(wind.unitsOfMeasure, context)
    return CurrentWeather(
        temp = tempStr,
        humidity = humidityStr,
        pressure = pressureStr,
        windSpeed = windSpeedStr,
        location = location.toLocation(),
        timestamp = timestamp
    )
}
