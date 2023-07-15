package ru.stolexiy.openweather

import android.app.Application
import android.os.Process
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(
                        priority, "[$GLOBAL_TAG] " +
                                "$tag | ${Process.getElapsedCpuTime()} ms", message, t
                    )
                }
            })
        }

        Thread.currentThread().setUncaughtExceptionHandler { _, error ->
            Timber.e(error, "Uncaught exception:")
        }
    }

    companion object {
        private const val GLOBAL_TAG: String = "OW"
    }
}
