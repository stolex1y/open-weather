package ru.stolexiy.openweather.ui.weather.forecast

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.navigation.BottomBarDestination
import ru.stolexiy.openweather.ui.weather.forecast.model.WeatherForecast

fun NavGraphBuilder.addWeatherForecast(
    onNavigateToRoute: (String) -> Unit
) {
    composable(WEATHER_FORECAST_DESTINATION.route) {
        WeatherForecast(onNavigateToRoute)
    }
}

val WEATHER_FORECAST_DESTINATION = object : BottomBarDestination {
    override val title: Int = R.string.forecast
    override val icon: Int = R.drawable.chart
    override val route: String = "weather-forecast"
}
