package ru.stolexiy.openweather.data.remote.dao

import retrofit2.http.GET
import ru.stolexiy.openweather.BuildConfig
import ru.stolexiy.openweather.data.remote.model.current.CurrentWeatherDto


interface CurrentWeatherDao {
    @GET("/weather?lat=59.894444&lon=30.264168&units=metric&lang=ru&appid=${BuildConfig.API_KEY}")
    fun getCurrentWeather(): CurrentWeatherDto
}
