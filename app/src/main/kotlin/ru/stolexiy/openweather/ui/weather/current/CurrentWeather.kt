package ru.stolexiy.openweather.ui.weather.current

import android.content.Context
import ru.stolexiy.openweather.domain.model.WeatherDetails
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPercents
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPressure
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature
import ru.stolexiy.openweather.ui.util.Formatters.formatAsWindSpeed

data class CurrentWeather(
    val temp: String?,
    val feelsLike: String?,
    val humidity: String?,
    val pressure: String?,
    val windSpeed: String?,
)

fun WeatherDetails.toCurrentWeather(context: Context): CurrentWeather {
    val tempStr = temperature.value?.formatAsTemperature(temperature.unitsOfTempMeasure)
    val feelsLikeStr = temperature.feelsLike?.formatAsTemperature(temperature.unitsOfTempMeasure)
    val humidityStr = humidity?.formatAsPercents()
    val pressureStr = pressure?.formatAsPressure(context)
    val windSpeedStr = wind.speed?.formatAsWindSpeed(wind.unitsOfMeasure, context)
    return CurrentWeather(
        temp = tempStr,
        feelsLike = feelsLikeStr,
        humidity = humidityStr,
        pressure = pressureStr,
        windSpeed = windSpeedStr,
    )
}
