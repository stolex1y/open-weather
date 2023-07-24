package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName

data class CoordinatesDto(
    @SerializedName("lon")
    var longitude: Double,
    @SerializedName("lat")
    var latitude: Double
)
