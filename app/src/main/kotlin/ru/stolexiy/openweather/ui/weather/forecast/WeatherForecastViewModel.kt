package ru.stolexiy.openweather.ui.weather.forecast

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.stolexiy.openweather.common.FlowExtensions.mapLatestResult
import ru.stolexiy.openweather.core.di.CoroutineModule
import ru.stolexiy.openweather.domain.model.DomainWeatherForecast
import ru.stolexiy.openweather.domain.repository.ForecastWeatherGettingRepository
import ru.stolexiy.openweather.ui.util.udf.AbstractViewModel
import ru.stolexiy.openweather.ui.util.udf.IData
import ru.stolexiy.openweather.ui.util.udf.IEvent
import ru.stolexiy.openweather.ui.util.udf.IState
import javax.inject.Named

class WeatherForecastViewModel @AssistedInject constructor(
    private val getWeatherForecast: ForecastWeatherGettingRepository,
    @Named(CoroutineModule.APPLICATION_SCOPE) applicationScope: CoroutineScope,
    @Assisted savedStateHandle: SavedStateHandle
) : AbstractViewModel<WeatherForecastViewModel.Event, WeatherForecastViewModel.Data, WeatherForecastViewModel.State>(
    Data(),
    stateProducer
) {

    fun refreshData() {
        dispatchEvent(Event.Load)
    }

    override fun dispatchEvent(event: Event) {
        when (event) {
            Event.Load -> startLoadingData()
        }
    }

    override fun dataFlow(): Flow<Result<Data>> {
        return getWeatherForecast.flow()
            .mapLatestResult { Data(it) }
    }

    companion object {
        private val stateProducer = object : IState.Producer<State> {
            override val init: State = State.Init
            override val loading: State = State.Loading
            override val loaded: State = State.Loaded

            override fun error(error: Int): State {
                return State.Error(error)
            }

            override fun isError(state: State): Boolean {
                return state is State.Error
            }
        }
    }

    data class Data(
        val forecasts: List<DomainWeatherForecast> = emptyList()
    ) : IData

    sealed interface State : IState {
        object Init : State
        object Loaded : State
        object Loading : State
        data class Error(@StringRes val error: Int) : State
    }

    sealed interface Event : IEvent {
        object Load : Event
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): WeatherForecastViewModel
    }
}