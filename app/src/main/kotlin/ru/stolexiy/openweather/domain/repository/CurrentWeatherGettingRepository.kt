package ru.stolexiy.openweather.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.stolexiy.openweather.domain.model.WeatherDetails

interface CurrentWeatherGettingRepository {
    fun flow(): Flow<Result<WeatherDetails>>
    suspend fun once(): Result<WeatherDetails>
}
