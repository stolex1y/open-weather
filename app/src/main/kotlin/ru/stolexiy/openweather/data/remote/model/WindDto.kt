package ru.stolexiy.openweather.data.remote.model

import ru.stolexiy.openweather.domain.model.DomainWind

data class WindDto(
    var speed: Double? = null,
    var deg: Int? = null,
    var gust: Double? = null,
) {
    fun toDomain() = DomainWind(
        speed = speed,
        deg = deg,
        gust = gust
    )
}
