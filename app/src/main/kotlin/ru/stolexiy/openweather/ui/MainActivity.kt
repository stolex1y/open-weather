package ru.stolexiy.openweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.databinding.ActivityMainBinding
import ru.stolexiy.openweather.databinding.ActivitySplashScreenBinding
import ru.stolexiy.openweather.ui.splash.LoadingTask


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Base_OpenWeather)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        showSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
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

    private fun setupNavigation() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navController.graph = navGraph
        binding.bottomNavigation.setupWithNavController(navController)
    }
}