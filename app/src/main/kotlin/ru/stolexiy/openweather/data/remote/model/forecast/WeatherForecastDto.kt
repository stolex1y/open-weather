package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.common.DateUtils.toCalendar
import ru.stolexiy.openweather.data.remote.model.CloudsDto
import ru.stolexiy.openweather.data.remote.model.WeatherDto
import ru.stolexiy.openweather.data.remote.model.WeatherMainParametersDto
import ru.stolexiy.openweather.data.remote.model.WindDto
import ru.stolexiy.openweather.domain.model.Location
import ru.stolexiy.openweather.domain.model.Precipitation
import ru.stolexiy.openweather.domain.model.WeatherForecast

data class WeatherForecastDto(
    override var main: WeatherMainParametersDto = WeatherMainParametersDto(),
    override var clouds: CloudsDto = CloudsDto(),
    override var wind: WindDto = WindDto(),
    @SerializedName("pop")
    var precipitationProb: Double? = null,
    @SerializedName("dt")
    override var timestamp: Long? = null,
    @SerializedName("rain")
    var rain: RainDto = RainDto(),
    @SerializedName("snow")
    var snow: SnowDto = SnowDto(),
) : WeatherDto {
    fun toDomain(city: CityDto) = WeatherForecast(
        location = Location(
            latitude = city.coordinates.latitude,
            longitude = city.coordinates.longitude,
            city = city.name,
            sunrise = city.sunrise?.toCalendar(),
            sunset = city.sunset?.toCalendar()
        ),
        temperature = main.toDomainTemperature(),
        pressure = main.pressure,
        humidity = main.humidity,
        clouds = clouds.cloudiness,
        precipitation = Precipitation(
            rain1h = null,
            rain3h = rain.volume3h,
            snow1h = null,
            snow3h = snow.volume3h
        ),
        wind = wind.toDomain(),
        timestamp = timestamp?.toCalendar(),
        precipitationProb = precipitationProb
    )
}
