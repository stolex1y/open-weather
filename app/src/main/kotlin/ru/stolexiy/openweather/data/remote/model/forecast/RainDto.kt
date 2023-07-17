package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName

data class RainDto(
    @SerializedName("3h")
    var volume3h: Double? = null
)
