package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.data.remote.model.CoordinatesDto

data class CityDto(
    var name: String?,
    @SerializedName("coord")
    var coordinates: CoordinatesDto,
    var sunrise: Long,
    var sunset: Long,
)
