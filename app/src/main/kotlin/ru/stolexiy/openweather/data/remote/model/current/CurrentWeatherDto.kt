package ru.stolexiy.openweather.data.remote.model.current

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.common.DateUtils.toCalendar
import ru.stolexiy.openweather.data.remote.model.CloudsDto
import ru.stolexiy.openweather.data.remote.model.CoordinatesDto
import ru.stolexiy.openweather.data.remote.model.WeatherDto
import ru.stolexiy.openweather.data.remote.model.WeatherMainParametersDto
import ru.stolexiy.openweather.data.remote.model.WindDto
import ru.stolexiy.openweather.domain.model.Location
import ru.stolexiy.openweather.domain.model.Precipitation
import ru.stolexiy.openweather.domain.model.WeatherDetails

data class CurrentWeatherDto(
    override var main: WeatherMainParametersDto = WeatherMainParametersDto(),
    override var clouds: CloudsDto = CloudsDto(),
    override var wind: WindDto = WindDto(),
    @SerializedName("dt")
    override var timestamp: Long? = null,
    @SerializedName("coord")
    var coordinates: CoordinatesDto = CoordinatesDto(),
    @SerializedName("rain")
    var rain: RainDto = RainDto(),
    @SerializedName("snow")
    var snow: SnowDto = SnowDto(),
    @SerializedName("sys")
    var sun: SunDto = SunDto(),
    @SerializedName("name")
    var city: String? = null,
) : WeatherDto {
    fun toDomainWeatherDetails() = WeatherDetails(
        location = Location(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude,
            city = city,
            sunrise = sun.sunrise?.toCalendar(),
            sunset = sun.sunset?.toCalendar()
        ),
        temperature = main.toDomainTemperature(),
        pressure = main.pressure,
        humidity = main.humidity,
        clouds = clouds.cloudiness,
        precipitation = Precipitation(
            rain1h = rain.volume1h,
            rain3h = rain.volume3h,
            snow1h = snow.volume1h,
            snow3h = snow.volume3h
        ),
        wind = wind.toDomain(),
        timestamp = timestamp?.toCalendar()
    )
}
