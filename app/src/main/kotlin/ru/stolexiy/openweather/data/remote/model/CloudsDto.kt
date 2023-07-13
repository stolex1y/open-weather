package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all")
    var cloudiness: Int? = null
)
