package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.domain.model.DomainTemperature

data class WeatherMainParametersDto(
    var temp: Double,
    @SerializedName("feels_like")
    var feelsLike: Double,
    @SerializedName("temp_min")
    var tempMin: Double,
    @SerializedName("temp_max")
    var tempMax: Double,
    var pressure: Int,
    var humidity: Int
) {
    fun toDomainTemperature() = DomainTemperature(
        value = temp,
        feelsLike = feelsLike,
        min = tempMin,
        max = tempMax,
    )
}