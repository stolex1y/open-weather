package ru.stolexiy.openweather.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.stolexiy.openweather.common.CoroutineDispatcherNames.DEFAULT_DISPATCHER
import ru.stolexiy.openweather.common.CoroutineDispatcherNames.IO_DISPATCHER
import ru.stolexiy.openweather.common.CoroutineDispatcherNames.MAIN_DISPATCHER
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoroutineModule {

    companion object {
        const val APPLICATION_SCOPE = "Application scope"

        @Named(IO_DISPATCHER)
        @Provides
        @Singleton
        fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Named(MAIN_DISPATCHER)
        @Provides
        @Singleton
        fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

        @Named(DEFAULT_DISPATCHER)
        @Provides
        @Singleton
        fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Named(APPLICATION_SCOPE)
        @Provides
        @Singleton
        fun applicationScope(
            @Named(MAIN_DISPATCHER) dispatcher: CoroutineDispatcher,
            exceptionHandler: CoroutineExceptionHandler
        ): CoroutineScope {
            return CoroutineScope(SupervisorJob() + exceptionHandler + dispatcher)
        }

        @Provides
        @Singleton
        fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->
            println(exception.stackTraceToString())
            Timber.e(exception, "uncaught exception")
        }
    }
}
