package ru.stolexiy.openweather.ui.weather.details

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import ru.stolexiy.openweather.ui.common.InfoColumn
import ru.stolexiy.openweather.ui.common.InfosRow
import ru.stolexiy.openweather.ui.common.OpenWeatherCard
import ru.stolexiy.openweather.ui.common.OpenWeatherTopAppBar
import ru.stolexiy.openweather.ui.common.PullToRefresh
import ru.stolexiy.openweather.ui.navigation.OpenWeatherBottomBar
import ru.stolexiy.openweather.ui.util.preview.PreviewData
import ru.stolexiy.openweather.ui.weather.current.CurrentWeatherViewModel
import ru.stolexiy.openweather.ui.weather.details.model.MainInfo
import ru.stolexiy.openweather.ui.weather.details.model.Precipitation
import ru.stolexiy.openweather.ui.weather.details.model.Temperature
import ru.stolexiy.openweather.ui.weather.details.model.WeatherDetails
import ru.stolexiy.openweather.ui.weather.details.model.Wind
import ru.stolexiy.openweather.ui.weather.details.model.toWeatherDetails
import timber.log.Timber

@Composable
fun CurrentWeatherDetails(
    onNavigateToRoute: (String) -> Unit
) {
    val viewModel: CurrentWeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val weatherDetails by viewModel.weatherDetailsState()
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
                title = weatherDetails?.location?.current ?: "",
                onRefresh = { viewModel.refreshData() }
            )
        },
        bottomBar = { BottomBar(onNavigateToRoute = onNavigateToRoute) },
    ) { insetsPadding ->
        Timber.d("bottom padding: ${insetsPadding.calculateBottomPadding()}")
        Content(
            modifier = Modifier
                .padding(insetsPadding),
            weatherDetails = weatherDetails,
            isLoading = state is CurrentWeatherViewModel.State.Loading,
            onRefresh = viewModel::refreshData
        )
        ErrorToast(state = state)
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    weatherDetails: WeatherDetails?,
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
                .padding(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 35.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)
        ) {
            MainInfoCard(mainInfo = weatherDetails?.mainInfo)
            TempCard(temp = weatherDetails?.temp)
            WindCard(wind = weatherDetails?.wind)
            PrecipitationCard(precipitation = weatherDetails?.precipitation)
        }
    }
}

@Composable
private fun MainInfoCard(mainInfo: MainInfo?) {
    InfoCard(name = R.string.main_info) {
        InfoColumn(name = R.string.humidity, value = mainInfo?.humidity)
        InfoColumn(name = R.string.visibility, value = mainInfo?.visibility)
        InfoColumn(name = R.string.pressure, value = mainInfo?.pressure)
    }
}

@Composable
private fun TempCard(temp: Temperature?) {
    InfoCard(name = R.string.temperature) {
        InfoColumn(name = R.string.feels_like, value = temp?.feelsLike)
        InfoColumn(name = R.string.max, value = temp?.max)
        InfoColumn(name = R.string.min, value = temp?.min)
    }
}

@Composable
private fun PrecipitationCard(precipitation: Precipitation?) {
    if (precipitation != null && precipitation.hasPrecipitation) {
        InfoCard(
            name = R.string.precipitation,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Image(
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxHeight(),
                painter = painterResource(id = precipitation.icon!!),
                contentDescription = stringResource(
                    id = precipitation.iconDescription!!
                )
            )
            InfoColumn(name = R.string.per_1_h, value = precipitation.per1h)
            InfoColumn(name = R.string.per_3_h, value = precipitation.per3h)
        }
    }
}

@Composable
private fun WindCard(wind: Wind?) {
    InfoCard(name = R.string.wind) {
        InfoColumn(name = R.string.direction, value = wind?.direction)
        InfoColumn(name = R.string.speed, value = wind?.speed)
        InfoColumn(name = R.string.gust, value = wind?.gust)
    }
}

@Composable
private fun BottomBar(
    onNavigateToRoute: (String) -> Unit
) {
    OpenWeatherBottomBar(
        currentTab = CURRENT_WEATHER_DETAILS_DESTINATION,
        onNavigateToRoute = onNavigateToRoute
    )
}

@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    rowInfos: @Composable RowScope.() -> Unit
) {
    OpenWeatherCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = stringResource(id = name), style = MaterialTheme.typography.titleLarge)
            InfosRow {
                rowInfos()
            }
        }
    }
}


@Composable
private fun getFakeWeatherDetails(): WeatherDetails {
    return PreviewData.getFakeDomainWeatherDetails().toWeatherDetails(LocalContext.current)
}

@Composable
private fun CurrentWeatherViewModel.weatherDetailsState(): State<WeatherDetails?> {
    val context = LocalContext.current
    return produceState<WeatherDetails?>(
        initialValue = null,
        key1 = context
    ) {
        data.map { it.currentWeather?.toWeatherDetails(context) }
            .collectLatest {
                value = it
            }
    }
}

@Composable
private fun ErrorToast(state: CurrentWeatherViewModel.State) {
    if (state is CurrentWeatherViewModel.State.Error) {
        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_LONG).show()
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ContentPreview() {
    Content(
        weatherDetails = getFakeWeatherDetails(),
        isLoading = false,
        onRefresh = {}
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ContentPreviewWithNull() {
    Content(
        weatherDetails = null,
        isLoading = false,
        onRefresh = {}
    )
}

