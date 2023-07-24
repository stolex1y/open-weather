package ru.stolexiy.openweather.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.domain.model.WeatherGroup
import java.util.Calendar

data class WeatherGroupDto(
    var id: Int,
    @SerializedName("main")
    var label: String,
    var description: String
) {
    fun toDomain(timestamp: Calendar, sunrise: Calendar, sunset: Calendar): WeatherGroup {
        return when (id) {
            in 200..299 -> WeatherGroup.THUNDERSTORM
            in 300..399, in 500..599 -> WeatherGroup.RAIN
            in 600..699 -> WeatherGroup.SNOW
            701 -> WeatherGroup.MIST
            800 -> if (timestamp.time.before(sunset.time) && timestamp.time.after(sunrise.time))
                WeatherGroup.CLEAR_DAY
            else
                WeatherGroup.CLEAR_NIGHT

            else -> WeatherGroup.CLOUDY
        }
    }
}
