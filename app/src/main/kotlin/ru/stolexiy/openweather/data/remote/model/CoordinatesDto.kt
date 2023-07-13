package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName

data class CoordinatesDto(
    @SerializedName("lon")
    var longitude: Double = 0.0,
    @SerializedName("lat")
    var latitude: Double = 0.0
)
