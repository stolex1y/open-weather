package ru.stolexiy.openweather.data.remote.model.current

import com.google.gson.annotations.SerializedName

data class RainDto(
    @SerializedName("1h")
    var volume1h: Double? = null,
    @SerializedName("3h")
    var volume3h: Double? = null
)
