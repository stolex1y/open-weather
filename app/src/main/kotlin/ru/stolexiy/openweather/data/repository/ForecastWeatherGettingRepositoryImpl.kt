package ru.stolexiy.openweather.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.stolexiy.openweather.common.CoroutineDispatcherNames
import ru.stolexiy.openweather.common.FlowExtensions.emitResult
import ru.stolexiy.openweather.data.remote.dao.ForecastWeatherRemoteDao
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import ru.stolexiy.openweather.domain.repository.ForecastWeatherGettingRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val SYNC_DELAY_MS: Long = 1000L * 60L * 10L

@Singleton
class ForecastWeatherGettingRepositoryImpl @Inject constructor(
    private val remoteDao: ForecastWeatherRemoteDao,
    @Named(CoroutineDispatcherNames.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) : ForecastWeatherGettingRepository {
    override fun flow(): Flow<Result<List<DomainWeatherForecast>>> {
        return flow {
            Timber.d("get weather forecast flow")
            while (true) {
                emitResult {
                    remoteDao.get5dayForecast().toDomain()
                }
                delay(SYNC_DELAY_MS)
            }
        }
            .distinctUntilChanged()
            .onEach { Timber.v("update weather forecast") }
            .flowOn(dispatcher)
    }

    override suspend fun once(): Result<List<DomainWeatherForecast>> = runCatching {
        withContext(dispatcher) {
            Timber.d("get weather forecast")
            remoteDao.get5dayForecast().toDomain()
        }
    }
}