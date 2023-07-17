package ru.stolexiy.openweather.ui.weather.forecast

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import ru.stolexiy.openweather.R

class ForecastWeatherFragmentToolbarProvider(
    private val context: Context,
    private val onRefresh: () -> Unit
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.current_weather_fragment_toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.refresh -> {
                onRefresh()
                true
            }

            else -> false
        }
    }
}