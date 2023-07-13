package ru.stolexiy.openweather.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.stolexiy.openweather.common.CoroutineDispatcherNames
import ru.stolexiy.openweather.common.FlowExtensions.mapToResult
import ru.stolexiy.openweather.data.remote.dao.CurrentWeatherRemoteDao
import ru.stolexiy.openweather.domain.model.WeatherDetails
import ru.stolexiy.openweather.domain.repository.CurrentWeatherGettingRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val SYNC_DELAY_MS: Long = 1000L * 60L * 5L

@Singleton
class CurrentWeatherGettingRepositoryImpl @Inject constructor(
    private val remoteDao: CurrentWeatherRemoteDao,
    @Named(CoroutineDispatcherNames.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) : CurrentWeatherGettingRepository {
    override fun flow(): Flow<Result<WeatherDetails>> {
        return flow {
            Timber.d("get current weather flow")
            while (true) {
                emit(remoteDao.getCurrentWeather().toDomain())
                delay(SYNC_DELAY_MS)
            }
        }
            .onEach { Timber.w("update current weather") }
            .flowOn(dispatcher)
            .mapToResult()
    }

    override suspend fun once(): Result<WeatherDetails> = runCatching {
        withContext(dispatcher) {
            remoteDao.getCurrentWeather().toDomain()
        }
    }
}
