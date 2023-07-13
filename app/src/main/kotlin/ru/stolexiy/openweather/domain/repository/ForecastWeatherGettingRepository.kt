package ru.stolexiy.openweather.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.stolexiy.openweather.domain.model.WeatherForecast

interface ForecastWeatherGettingRepository {
    fun flow(): Flow<Result<List<WeatherForecast>>>
    suspend fun once(): Result<List<WeatherForecast>>
}