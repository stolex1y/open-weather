package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.data.remote.model.CloudsDto
import ru.stolexiy.openweather.data.remote.model.WeatherDto
import ru.stolexiy.openweather.data.remote.model.WeatherMainParametersDto
import ru.stolexiy.openweather.data.remote.model.WindDto

data class WeatherForecastDto(
    override var main: WeatherMainParametersDto = WeatherMainParametersDto(),
    override var clouds: CloudsDto = CloudsDto(),
    override var wind: WindDto = WindDto(),
    @SerializedName("pop")
    var precipitationProb: Double? = null,
    @SerializedName("dt")
    override var timestamp: Long? = null,
    @SerializedName("rain")
    var rain: RainDto? = null,
    @SerializedName("snow")
    var snow: SnowDto? = null,
) : WeatherDto
