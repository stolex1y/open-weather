package ru.stolexiy.openweather.ui.util.udf

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.stolexiy.openweather.R
import timber.log.Timber
import java.net.UnknownHostException

abstract class AbstractViewModel<E : IEvent, D : IData, S : IState>(
    initData: D,
    private val stateProducer: IState.Producer<S>,
) : ViewModel() {

    private var dataLoadingJob: Job? = null

    private val _state: MutableStateFlow<S> = MutableStateFlow(stateProducer.init)
    val state: StateFlow<S> = _state.asStateFlow()
    var prevState: S = stateProducer.init
        private set

    private val _data: MutableStateFlow<D> = MutableStateFlow(initData)
    val data: StateFlow<D> = _data.asStateFlow()

    abstract fun dispatchEvent(event: E)

    protected abstract fun dataFlow(): Flow<Result<D>>

    private fun setErrorStateWith(@StringRes errorMsg: Int) {
        updateState(stateProducer.error(errorMsg))
    }

    protected fun startLoadingData() {
        val currentLoadingJob = dataLoadingJob
        if (currentLoadingJob != null) {
            currentLoadingJob.cancel()
            dataLoadingJob = null
        }
        updateState(stateProducer.loading)
        dataLoadingJob = viewModelScope.launch {
            dataFlow().handleError()
                .mapNotNull { it.getOrNull() }
                .onStart { Timber.d("start loading data") }
                .onCompletion { Timber.d("stop loading data") }
                .collectLatest {
                    _data.value = it
                    if (state.value == stateProducer.loading || stateProducer.isError(state.value))
                        updateState(stateProducer.loaded)
                }
        }
    }

    @StringRes
    protected open fun parseError(error: Throwable): Int {
        return when (error) {
            is UnknownHostException -> R.string.service_unavailable
            else -> R.string.internal_error
        }
    }

    protected fun updateState(state: S) {
        prevState = _state.value
        _state.value = state
    }

    protected fun updateState(error: Throwable) {
        Timber.e(error, "Updated state with error:")
        setErrorStateWith(parseError(error))
    }

    private fun <T> Flow<Result<T>>.handleError(): Flow<Result<T>> {
        return this.onEach {
            if (it.isFailure) {
                Timber.e(it.exceptionOrNull()!!, "error in flow")
                updateState(it.exceptionOrNull()!!)
            }
        }
    }
}
