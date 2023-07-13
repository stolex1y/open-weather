package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName

data class SnowDto(
    @SerializedName("3h")
    var volume3h: Int? = null
)
