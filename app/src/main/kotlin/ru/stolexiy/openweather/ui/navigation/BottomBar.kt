package ru.stolexiy.openweather.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.stolexiy.openweather.ui.weather.current.CURRENT_WEATHER_DESTINATION
import ru.stolexiy.openweather.ui.weather.details.CURRENT_WEATHER_DETAILS_DESTINATION
import ru.stolexiy.openweather.ui.weather.forecast.WEATHER_FORECAST_DESTINATION

val MAIN_DESTINATIONS: Array<out BottomBarDestination> = arrayOf(
    CURRENT_WEATHER_DETAILS_DESTINATION,
    CURRENT_WEATHER_DESTINATION,
    WEATHER_FORECAST_DESTINATION
)

@Composable
fun OpenWeatherBottomBar(
    tabs: Array<out BottomBarDestination> = MAIN_DESTINATIONS,
    currentTab: BottomBarDestination,
    onNavigateToRoute: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                selected = tab == currentTab,
                onClick = { onNavigateToRoute(tab.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(id = tab.title)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    OpenWeatherBottomBar(currentTab = MAIN_DESTINATIONS.first(), onNavigateToRoute = {})
}

interface BottomBarDestination {
    @get:StringRes
    val title: Int

    @get:DrawableRes
    val icon: Int

    val route: String
}
