package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.domain.model.WeatherGroup

data class WeatherGroupDto(
    var id: Int = 0,
    @SerializedName("main")
    var label: String = "",
    var description: String = ""
) {
    fun toDomain(): WeatherGroup {
        return when (id) {
            in 200..299 -> WeatherGroup.THUNDERSTORM
            in 300..399, in 500..599 -> WeatherGroup.RAIN
            in 600..699 -> WeatherGroup.SNOW
            701 -> WeatherGroup.MIST
            800 -> WeatherGroup.CLEAR
            else -> WeatherGroup.CLOUDY
        }
    }
}
