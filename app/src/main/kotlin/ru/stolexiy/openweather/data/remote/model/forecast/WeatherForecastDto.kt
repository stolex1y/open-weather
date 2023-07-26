package ru.stolexiy.openweather.data.remote.model.forecast

import com.google.gson.annotations.SerializedName
import ru.stolexiy.openweather.common.DateUtils.toCalendarFromSeconds
import ru.stolexiy.openweather.data.remote.model.CloudsDto
import ru.stolexiy.openweather.data.remote.model.WeatherDto
import ru.stolexiy.openweather.data.remote.model.WeatherGroupDto
import ru.stolexiy.openweather.data.remote.model.WeatherMainParametersDto
import ru.stolexiy.openweather.data.remote.model.WindDto
import ru.stolexiy.openweather.domain.model.DomainLocation
import ru.stolexiy.openweather.domain.model.DomainPrecipitation
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import java.util.Calendar

data class WeatherForecastDto(
    override var main: WeatherMainParametersDto,
    override var visibility: Int,
    override var clouds: CloudsDto,
    override var wind: WindDto?,
    @SerializedName("pop")
    var precipitationProb: Double,
    @SerializedName("dt")
    override var timestamp: Long,
    @SerializedName("rain")
    var rain: RainDto?,
    @SerializedName("snow")
    var snow: SnowDto?,
    @SerializedName("weather")
    override var weatherGroups: List<WeatherGroupDto>,
) : WeatherDto {
    fun toDomain(city: CityDto): DomainWeatherForecast {
        val timestampCalendar = timestamp.toCalendarFromSeconds()
        val sunriseCalendar = city.sunrise.toCalendarFromSeconds()
        val sunsetCalendar = city.sunset.toCalendarFromSeconds()

        return DomainWeatherForecast(
            location = DomainLocation(
                latitude = city.coordinates.latitude,
                longitude = city.coordinates.longitude,
                city = city.name,
                sunrise = sunriseCalendar.clone() as Calendar,
                sunset = sunsetCalendar.clone() as Calendar
            ),
            temperature = main.toDomainTemperature(),
            pressure = main.pressure,
            humidity = main.humidity,
            clouds = clouds.cloudiness,
            precipitation = DomainPrecipitation(
                rain1h = null,
                rain3h = rain?.volume3h,
                snow1h = null,
                snow3h = snow?.volume3h
            ),
            wind = (wind ?: WindDto()).toDomain(),
            timestamp = timestampCalendar.clone() as Calendar,
            precipitationProb = precipitationProb * 100,
            visibility = visibility,
            weatherGroup = weatherGroups.first().toDomain(
                timestamp = timestampCalendar.clone() as Calendar,
                sunrise = sunriseCalendar.clone() as Calendar,
                sunset = sunsetCalendar.clone() as Calendar
            ),
        )
    }
}
