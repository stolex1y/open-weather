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
import ru.stolexiy.openweather.data.remote.dao.CurrentWeatherRemoteDao
import ru.stolexiy.openweather.domain.model.DomainWeatherDetails
import ru.stolexiy.openweather.domain.repository.CurrentWeatherGettingRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val SYNC_DELAY_MS: Long = 1000L * 60L * 10L

@Singleton
class CurrentWeatherGettingRepositoryImpl @Inject constructor(
    private val remoteDao: CurrentWeatherRemoteDao,
    @Named(CoroutineDispatcherNames.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) : CurrentWeatherGettingRepository {
    override fun flow(): Flow<Result<DomainWeatherDetails>> {
        return flow {
            Timber.d("get current weather flow")
            while (true) {
                emitResult { remoteDao.getCurrentWeather().toDomain() }
                delay(SYNC_DELAY_MS)
            }
        }
            .distinctUntilChanged()
            .onEach { Timber.v("update current weather") }
            .flowOn(dispatcher)
    }

    override suspend fun once(): Result<DomainWeatherDetails> = runCatching {
        withContext(dispatcher) {
            remoteDao.getCurrentWeather().toDomain()
        }
    }
}
