package ru.stolexiy.openweather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import ru.stolexiy.openweather.ui.navigation.rememberOpenWeatherNavController
import ru.stolexiy.openweather.ui.theme.OpenWeatherTheme
import ru.stolexiy.openweather.ui.weather.current.CURRENT_WEATHER_DESTINATION
import ru.stolexiy.openweather.ui.weather.current.addCurrentWeather
import ru.stolexiy.openweather.ui.weather.details.addCurrentWeatherDetails
import ru.stolexiy.openweather.ui.weather.forecast.addWeatherForecast

@Composable
fun OpenWeatherApp() {
    OpenWeatherTheme {
        val openWeatherNavController = rememberOpenWeatherNavController()
        NavHost(
            navController = openWeatherNavController.navController,
            startDestination = CURRENT_WEATHER_DESTINATION.route
        ) {
            openWeatherNavGraph(
                onNavigateToRoute = openWeatherNavController::navigateToRoute
            )
        }
    }
}

private fun NavGraphBuilder.openWeatherNavGraph(
    onNavigateToRoute: (String) -> Unit
) {
    addCurrentWeather(
        onNavigateToRoute
    )
    addWeatherForecast(onNavigateToRoute)
    addCurrentWeatherDetails(onNavigateToRoute)
}
