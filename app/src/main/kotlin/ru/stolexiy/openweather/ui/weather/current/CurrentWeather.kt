package ru.stolexiy.openweather.ui.weather.current

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.ui.util.Formatters.DMY_DATE
import ru.stolexiy.openweather.ui.util.Formatters.HM_TIME
import ru.stolexiy.openweather.ui.util.Formatters.toString
import java.util.Calendar

data class CurrentWeather(
    val timestamp: Calendar,
    val mainInfo: MainInfo,
    val location: Location,
) {
    val dateStr: String = timestamp.toString(DMY_DATE)
    val timeStr: String = timestamp.toString(HM_TIME)
}

fun DomainWeatherDetails.toCurrentWeather(context: Context): CurrentWeather {
    return CurrentWeather(
        mainInfo = this.toMainInfo(context),
        location = location.toLocation(),
        timestamp = timestamp
    )
}
