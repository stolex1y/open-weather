package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.common.DateUtils.toCalendar
import ru.stolexiy.openweather.common.DateUtils.toCalendarFromSeconds
import ru.stolexiy.openweather.data.remote.model.CloudsDto
import ru.stolexiy.openweather.data.remote.model.WeatherDto
import ru.stolexiy.openweather.data.remote.model.WeatherMainParametersDto
import ru.stolexiy.openweather.data.remote.model.WindDto
import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.domain.model.DomainPrecipitation
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast

data class WeatherForecastDto(
    override var main: WeatherMainParametersDto = WeatherMainParametersDto(),
    override var visibility: Int?,
    override var clouds: CloudsDto?,
    override var wind: WindDto?,
    @SerializedName("pop")
    var precipitationProb: Double?,
    @SerializedName("dt")
    override var timestamp: Long = 0L,
    @SerializedName("rain")
    var rain: RainDto?,
    @SerializedName("snow")
    var snow: SnowDto?,
) : WeatherDto {
    fun toDomain(city: CityDto) = DomainWeatherForecast(
        location = DomainLocation(
            latitude = city.coordinates.latitude,
            longitude = city.coordinates.longitude,
            city = city.name,
            sunrise = city.sunrise.toCalendarFromSeconds(),
            sunset = city.sunset.toCalendarFromSeconds()
        ),
        temperature = main.toDomainTemperature(),
        pressure = main.pressure,
        humidity = main.humidity,
        clouds = clouds?.cloudiness,
        precipitation = DomainPrecipitation(
            rain1h = null,
            rain3h = rain?.volume3h,
            snow1h = null,
            snow3h = snow?.volume3h
        ),
        wind = (wind ?: WindDto()).toDomain(),
        timestamp = timestamp.toCalendarFromSeconds(),
        precipitationProb = precipitationProb,
        visibility = visibility,
    )
}
