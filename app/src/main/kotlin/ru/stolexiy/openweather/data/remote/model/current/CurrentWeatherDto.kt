package ru.stolexiy.openweather.data.remote.model.current

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.common.DateUtils.toCalendar
import ru.stolexiy.openweather.data.remote.model.CloudsDto
import ru.stolexiy.openweather.data.remote.model.CoordinatesDto
import ru.stolexiy.openweather.data.remote.model.WeatherDto
import ru.stolexiy.openweather.data.remote.model.WeatherMainParametersDto
import ru.stolexiy.openweather.data.remote.model.WindDto
import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.domain.model.DomainPrecipitation
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails

data class CurrentWeatherDto(
    override var main: WeatherMainParametersDto,
    override var visibility: Int?,
    override var clouds: CloudsDto?,
    override var wind: WindDto?,
    @SerializedName("dt")
    override var timestamp: Long?,
    @SerializedName("coord")
    var coordinates: CoordinatesDto = CoordinatesDto(),
    @SerializedName("rain")
    var rain: RainDto?,
    @SerializedName("snow")
    var snow: SnowDto?,
    @SerializedName("sys")
    var sun: SunDto = SunDto(),
    @SerializedName("name")
    var city: String?,
) : WeatherDto {
    fun toDomain() = DomainWeatherDetails(
        location = DomainLocation(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude,
            city = city,
            sunrise = sun.sunrise.toCalendar(),
            sunset = sun.sunset.toCalendar()
        ),
        temperature = main.toDomainTemperature(),
        pressure = main.pressure,
        humidity = main.humidity,
        clouds = clouds?.cloudiness,
        precipitation = DomainPrecipitation(
            rain1h = rain?.volume1h,
            rain3h = rain?.volume3h,
            snow1h = snow?.volume1h,
            snow3h = snow?.volume3h
        ),
        wind = (wind ?: WindDto()).toDomain(),
        timestamp = timestamp?.toCalendar(),
        visibility = visibility
    )
}
