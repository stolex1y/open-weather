package ru.stolexiy.openweather.ui.weather.current

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.common.DrawableImage
import ru.stolexiy.openweather.ui.common.InfoColumn
import ru.stolexiy.openweather.ui.common.MainInfoCard
import ru.stolexiy.openweather.ui.common.OpenWeatherCard
import ru.stolexiy.openweather.ui.common.OpenWeatherTopAppBar
import ru.stolexiy.openweather.ui.common.PullToRefresh
import ru.stolexiy.openweather.ui.common.TextWithDefaultValue
import ru.stolexiy.openweather.ui.navigation.OpenWeatherBottomBar
import ru.stolexiy.openweather.ui.util.preview.PreviewData
import ru.stolexiy.openweather.ui.weather.current.model.MainInfo
import ru.stolexiy.openweather.ui.weather.current.model.Weather
import ru.stolexiy.openweather.ui.weather.current.model.toWeather

@Composable
fun CurrentWeather(
    onNavigateToRoute: (String) -> Unit,
) {
    val viewModel: CurrentWeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val weather by viewModel.currentWeatherState()
    LaunchedEffect(key1 = viewModel) {
        viewModel.refreshData()
    }
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.background)
            ),
        topBar = {
            OpenWeatherTopAppBar(
                title = weather?.location?.current ?: "",
                onRefresh = { viewModel.refreshData() }
            )
        },
        bottomBar = { BottomBar(onNavigateToRoute = onNavigateToRoute) },
    ) { insetsPadding ->
        Content(
            modifier = Modifier
                .fillMaxSize()
                .padding(insetsPadding),
            weather = weather,
            isLoading = state is CurrentWeatherViewModel.State.Loading,
            onRefresh = viewModel::refreshData
        )
        ErrorToast(state = state)
    }
}

@Composable
private fun ErrorToast(state: CurrentWeatherViewModel.State) {
    if (state is CurrentWeatherViewModel.State.Error) {
        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_LONG).show()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    weather: Weather?,
    isLoading: Boolean,
    onRefresh: () -> Unit
) {
    PullToRefresh(
        modifier = modifier,
        isLoading = isLoading,
        onRefresh = onRefresh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 25.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)
        ) {
            MainInfo(
                modifier = Modifier.fillMaxWidth(),
                mainInfo = weather?.mainInfo
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SmallCard(
                    modifier = Modifier.weight(1f),
                    name = R.string.sunrise,
                    infoValue = weather?.location?.sunriseStr,
                    infoImage = R.drawable.sunrise
                )
                SmallCard(
                    modifier = Modifier.weight(1f),
                    name = R.string.sunset,
                    infoValue = weather?.location?.sunsetStr,
                    infoImage = R.drawable.sunset
                )
            }
        }
    }
}

@Composable
private fun MainInfo(
    modifier: Modifier = Modifier,
    mainInfo: MainInfo?
) {
    MainInfoCard(
        modifier = modifier,
        date = mainInfo?.dateStr,
        time = mainInfo?.timeStr,
        temperature = mainInfo?.temperature,
        weatherGroupIcon = mainInfo?.weatherGroupIcon,
        weatherGroupLabel = mainInfo?.weatherGroupLabel
    ) {
        item {
            InfoColumn(
                name = R.string.humidity,
                value = mainInfo?.humidity
            )
        }
        item {
            InfoColumn(
                name = R.string.wind_speed,
                value = mainInfo?.windSpeed
            )
        }
        item {
            InfoColumn(
                name = R.string.pressure,
                value = mainInfo?.pressure
            )
        }
    }
}

@Composable
private fun SmallCard(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    infoValue: String?,
    @DrawableRes infoImage: Int? = null
) {
    OpenWeatherCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 34.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = name),
                style = MaterialTheme.typography.labelMedium
            )
            DrawableImage(
                resourceId = infoImage,
                contentDescription = null
            )
            TextWithDefaultValue(
                text = infoValue,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
private fun BottomBar(
    onNavigateToRoute: (String) -> Unit
) {
    OpenWeatherBottomBar(
        currentTab = CURRENT_WEATHER_DESTINATION,
        onNavigateToRoute = onNavigateToRoute
    )
}

@Composable
private fun getFakeCurrentWeather(): Weather {
    return PreviewData.getFakeDomainWeatherDetails().toWeather(LocalContext.current)
}

@Composable
private fun CurrentWeatherViewModel.currentWeatherState(): State<Weather?> {
    val context = LocalContext.current
    return produceState<Weather?>(initialValue = null, key1 = context) {
        data.map { it.currentWeather?.toWeather(context) }
            .collectLatest {
                value = it
            }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ContentPreview() {
    Content(
        weather = getFakeCurrentWeather(),
        isLoading = false,
        onRefresh = {}
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun ContentPreviewWithNull() {
    Content(
        weather = null,
        isLoading = false,
        onRefresh = {}
    )
}
