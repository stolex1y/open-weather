package ru.stolexiy.openweather.ui.weather.details.model

import android.content.Context
import ru.stolexiy.openweather.domain.model.DomainWind
import ru.stolexiy.openweather.ui.util.Formatters.formatAsWindSpeed
import ru.stolexiy.openweather.ui.util.Formatters.parseWindDirection

data class Wind(
    val speed: String?,
    val direction: String?,
    val gust: String?,
)

fun DomainWind.toWind(context: Context) = Wind(
    speed = speed?.formatAsWindSpeed(unitsOfMeasure, context),
    direction = deg?.parseWindDirection(context),
    gust = gust?.formatAsWindSpeed(unitsOfMeasure, context),
)
