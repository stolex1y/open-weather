package ru.stolexiy.openweather.data.remote.dao

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import ru.stolexiy.openweather.data.remote.model.forecast.CityDto
import ru.stolexiy.openweather.data.remote.model.forecast.WeatherForecastDto
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast

interface ForecastWeatherRemoteDao {

    @GET("forecast?lat=59.894444&lon=30.264168&units=metric&lang=ru")
    suspend fun get5dayForecast(): Response

    data class Response(
        @SerializedName("list")
        var forecast: List<WeatherForecastDto>,
        var city: CityDto = CityDto(),
    ) {
        fun toDomain(): List<DomainWeatherForecast> = forecast.map {
            it.toDomain(city)
        }
    }
}
