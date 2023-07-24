package ru.stolexiy.openweather.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.stolexiy.openweather.databinding.ActivitySplashScreenBinding
import ru.stolexiy.openweather.ui.splash.LoadingTask


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        showSplashScreen()
    }

    private fun showSplashScreen() {
        val splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)
        LoadingTask(100, splashScreenBinding.loadingIndicator).apply {
            onLoadingEnd = {
                showMainScreen()
            }

        }.startLoading()
    }

    private fun showMainScreen() {
        setContent {
            OpenWeatherApp()
        }
    }
}
