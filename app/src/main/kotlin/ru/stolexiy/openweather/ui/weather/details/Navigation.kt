package ru.stolexiy.openweather.ui.weather.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.navigation.BottomBarDestination

fun NavGraphBuilder.addCurrentWeatherDetails(
    onNavigateToBottomBarRoute: (String) -> Unit
) {
    composable(CURRENT_WEATHER_DETAILS_DESTINATION.route) {
        CurrentWeatherDetails(
            onNavigateToRoute = onNavigateToBottomBarRoute
        )
    }
}

val CURRENT_WEATHER_DETAILS_DESTINATION = object : BottomBarDestination {
    override val title: Int = R.string.details
    override val icon: Int = R.drawable.list
    override val route: String = "current-weather-details"
}
