package ru.stolexiy.openweather.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast

interface ForecastWeatherGettingRepository {
    fun flow(): Flow<Result<List<DomainWeatherForecast>>>
    suspend fun once(): Result<List<DomainWeatherForecast>>
}