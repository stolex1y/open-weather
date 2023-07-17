package ru.stolexiy.openweather.ui.weather.current

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.util.MenuUtils.inflateWithTintIcons

class CurrentWeatherFragmentToolbarProvider(
    private val context: Context,
    private val onRefresh: () -> Unit
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflateWithTintIcons(
            R.menu.current_weather_fragment_toolbar_menu,
            menu,
            com.google.android.material.R.attr.colorOnBackground,
            context
        )
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