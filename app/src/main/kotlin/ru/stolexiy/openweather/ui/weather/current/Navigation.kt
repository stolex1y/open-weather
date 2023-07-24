package ru.stolexiy.openweather.ui.weather.current

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.navigation.BottomBarDestination

fun NavGraphBuilder.addCurrentWeather(
    onNavigateToRoute: (String) -> Unit
) {
    composable(
        route = CURRENT_WEATHER_DESTINATION.route
    ) {
        CurrentWeather(
            onNavigateToRoute = onNavigateToRoute
        )
    }
}

val CURRENT_WEATHER_DESTINATION = object : BottomBarDestination {
    override val title: Int = R.string.now
    override val icon: Int = R.drawable.clock
    override val route: String = "current-weather"
}
