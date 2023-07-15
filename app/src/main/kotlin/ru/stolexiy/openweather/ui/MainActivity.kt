package ru.stolexiy.openweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.databinding.ActivityMainBinding
import ru.stolexiy.openweather.databinding.ActivitySplashScreenBinding
import ru.stolexiy.openweather.ui.splash.LoadingTask
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_OpenWeather)
        super.onCreate(savedInstanceState)
        showSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            val navController =
                (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
            val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
            navController.graph = navGraph
            bottomNavigation.setupWithNavController(navController)
        }
    }

    private fun showSplashScreen() {
        val splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)
        LoadingTask(1000, splashScreenBinding.loadingIndicator).apply {
            onLoadingEnd = {
                showMainScreen()
            }

        }.startLoading()
    }

    private fun showMainScreen() {
        setContentView(binding.root)
    }
}