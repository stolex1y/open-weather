package ru.stolexiy.openweather.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stolexiy.openweather.data.repository.CurrentWeatherGettingRepositoryImpl
import ru.stolexiy.openweather.data.repository.ForecastWeatherGettingRepositoryImpl
import ru.stolexiy.openweather.domain.repository.CurrentWeatherGettingRepository
import ru.stolexiy.openweather.domain.repository.ForecastWeatherGettingRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun currentWeatherGettingRepository(i: CurrentWeatherGettingRepositoryImpl): CurrentWeatherGettingRepository

    @Binds
    fun forecastWeatherGettingRepository(i: ForecastWeatherGettingRepositoryImpl): ForecastWeatherGettingRepository
}
