package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.domain.model.Temperature

data class WeatherMainParametersDto(
    var temp: Double? = null,
    @SerializedName("feels_like")
    var feelsLike: Double? = null,
    @SerializedName("temp_min")
    var tempMin: Double? = null,
    @SerializedName("temp_max")
    var tempMax: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null
) {
    fun toDomainTemperature() = Temperature(
        value = temp,
        feelsLike = feelsLike,
        min = tempMin,
        max = tempMax,
    )
}