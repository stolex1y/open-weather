package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.data.remote.model.CoordinatesDto

data class CityDto(
    var name: String? = null,
    @SerializedName("coord")
    var coordinates: CoordinatesDto = CoordinatesDto(),
    var sunrise: Long = 0,
    var sunset: Long = 0,
)
