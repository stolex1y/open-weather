package ru.stolexiy.openweather.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails

interface CurrentWeatherGettingRepository {
    fun flow(): Flow<Result<DomainWeatherDetails>>
    suspend fun once(): Result<DomainWeatherDetails>
}
