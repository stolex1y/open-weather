package ru.stolexiy.openweather.domain.model

data class Precipitation(
    val rain1h: Int?,
    val rain3h: Int?,
    val snow1h: Int?,
    val snow3h: Int?,
)
