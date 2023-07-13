package ru.stolexiy.openweather.data.remote.dao

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import ru.stolexiy.openweather.BuildConfig
import ru.stolexiy.openweather.data.remote.model.forecast.CityDto
import ru.stolexiy.openweather.data.remote.model.forecast.WeatherForecastDto

interface ForecastWeatherDao {

    @GET("/forecast?lat=59.894444&lon=30.264168&units=metric&lang=ru&appid=${BuildConfig.API_KEY}")
    suspend fun get5dayForecast(): Response

    data class Response(
        @SerializedName("list")
        var forecast: List<WeatherForecastDto>,
        var city: CityDto? = null,
    )
}
