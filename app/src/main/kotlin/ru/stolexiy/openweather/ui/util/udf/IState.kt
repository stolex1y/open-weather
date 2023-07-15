package ru.stolexiy.openweather.ui.util.udf

import androidx.annotation.StringRes

interface IState {
    interface Producer<S : IState> {
        val init: S
        val loading: S
        val loaded: S
        fun error(@StringRes error: Int): S
        fun isError(state: S): Boolean
    }
}
