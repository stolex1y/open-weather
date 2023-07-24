package ru.stolexiy.openweather.core.di

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Provider

inline fun <reified EP> entryPointApplicationAccessor(contextProvider: Provider<Context>): Lazy<EP> {
    return lazy {
        EntryPointAccessors.fromApplication<EP>(contextProvider.get())
    }
}

@Composable
inline fun <reified EP> entryPointByActivityAccessor(): EP {
    return EntryPointAccessors.fromActivity<EP>(LocalContext.current as Activity)
}
