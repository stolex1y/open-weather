package ru.stolexiy.openweather.data.remote.model

import ru.stolexiy.openweather.domain.model.Wind

data class WindDto(
    var speed: Double? = null,
    var deg: Int? = null,
    var gust: Double? = null,
) {
    fun toDomain() = Wind(
        speed = speed,
        deg = deg,
        gust = gust
    )
}
