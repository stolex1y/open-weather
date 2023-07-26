package ru.stolexiy.openweather.ui.weather.forecast

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.common.InfoColumn
import ru.stolexiy.openweather.ui.common.MainInfoCard
import ru.stolexiy.openweather.ui.common.OpenWeatherTopAppBar
import ru.stolexiy.openweather.ui.common.PullToRefresh
import ru.stolexiy.openweather.ui.navigation.OpenWeatherBottomBar
import ru.stolexiy.openweather.ui.util.Formatters
import ru.stolexiy.openweather.ui.util.Formatters.formatAsDistance
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPercents
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPressure
import ru.stolexiy.openweather.ui.util.Formatters.formatAsTemperature
import ru.stolexiy.openweather.ui.util.Formatters.formatAsWindSpeed
import ru.stolexiy.openweather.ui.util.Formatters.parseWindDirection
import ru.stolexiy.openweather.ui.util.Formatters.toString
import ru.stolexiy.openweather.ui.util.preview.PreviewData
import ru.stolexiy.openweather.ui.weather.forecast.model.MainInfo
import ru.stolexiy.openweather.ui.weather.forecast.model.WeatherForecast
import ru.stolexiy.openweather.ui.weather.forecast.model.toWeatherForecast
import timber.log.Timber
import java.util.Calendar

@Composable
fun WeatherForecast(
    onNavigateToRoute: (String) -> Unit
) {
    val viewModel: WeatherForecastViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val forecasts by viewModel.weatherForecastState()
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
                title = forecasts.firstOrNull()?.location?.current ?: "",
                onRefresh = { viewModel.refreshData() }
            )
        },
        bottomBar = { BottomBar(onNavigateToRoute = onNavigateToRoute) },
    ) { insetsPadding ->
        Timber.d("bottom padding: ${insetsPadding.calculateBottomPadding()}")
        Content(
            modifier = Modifier
                .padding(insetsPadding),
            forecasts = forecasts,
            isLoading = state is WeatherForecastViewModel.State.Loading,
            onRefresh = viewModel::refreshData
        )
        ErrorToast(state = state)
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    forecasts: List<WeatherForecast>,
    isLoading: Boolean,
    onRefresh: () -> Unit
) {
    var selectedForecast: Int? by remember {
        mutableStateOf(null)
    }
    val mainInfos: List<MainInfo> = remember(forecasts) {
        forecasts.map { it.mainInfo }
    }
    PullToRefresh(
        modifier = modifier,
        isLoading = isLoading,
        onRefresh = onRefresh
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 25.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)
        ) {
            ForecastsRow(
                modifier = Modifier.systemGestureExclusion(),
                forecasts = mainInfos,
                onSelectForecast = {
                    selectedForecast = it
                }
            )
            ForecastDetails(
                forecast = forecasts.getOrNull(selectedForecast ?: -1),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
private fun ForecastsRow(
    modifier: Modifier,
    forecasts: List<MainInfo>,
    onSelectForecast: (Int?) -> Unit
) {
    var selectedItem: Int? by remember {
        mutableStateOf(null)
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier,
        contentPadding = PaddingValues(start = 25.dp, end = 25.dp)
    ) {
        itemsIndexed(
            items = forecasts,
            key = { _, item -> item.timestamp.timeInMillis }
        ) { index, item ->
            val prev = forecasts.getOrNull(index - 1)
            ForecastCard(
                forecast = item,
                showDate = shouldShowDate(prevForecast = prev, currForecast = item),
                selected = selectedItem == index,
                modifier = Modifier.clickable {
                    selectedItem = if (selectedItem == index)
                        null
                    else
                        index
                    onSelectForecast(selectedItem)
                }
            )
        }
    }
}

@Composable
private fun ForecastCard(
    modifier: Modifier = Modifier,
    forecast: MainInfo,
    showDate: Boolean,
    selected: Boolean,
) {
    val background = if (selected)
        MaterialTheme.colorScheme.primaryContainer
    else
        Color.Transparent
    val shadow = if (selected)
        Modifier.shadow(25.dp)
    else
        Modifier
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .then(shadow)
            .clip(MaterialTheme.shapes.medium)
            .background(background)
            .padding(5.dp)
    ) {
        Text(
            text = forecast.timestamp.toString(Formatters.HM_TIME),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = if (showDate)
                forecast.timestamp.toString(Formatters.DM_DATE)
            else
                "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Image(
            painter = painterResource(id = forecast.weatherGroupIcon),
            contentDescription = stringResource(
                id = forecast.weatherGroupLabel
            ),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
        Text(
            text = forecast.temperature.formatAsTemperature(forecast.unitsOfTempMeasure),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ForecastDetails(
    modifier: Modifier = Modifier,
    forecast: WeatherForecast?
) {
    if (forecast == null)
        return
    val mainInfo = forecast.mainInfo
    val details = forecast.details
    val context = LocalContext.current
    MainInfoCard(
        modifier = modifier,
        date = mainInfo.timestamp.toString(Formatters.DMY_DATE),
        time = mainInfo.timestamp.toString(Formatters.HM_TIME),
        temperature = mainInfo.temperature.formatAsTemperature(mainInfo.unitsOfTempMeasure),
        weatherGroupIcon = mainInfo.weatherGroupIcon,
        weatherGroupLabel = mainInfo.weatherGroupLabel,
        itemsInRow = 2
    ) {
        item {
            InfoColumn(
                name = R.string.wind_speed,
                value = details.windSpeed?.formatAsWindSpeed(details.unitsOfWindMeasure, context)
            )
        }
        item {
            InfoColumn(
                name = R.string.wind_direction,
                value = details.windDeg?.parseWindDirection(context)
            )
        }
        item {
            InfoColumn(
                name = R.string.humidity,
                value = details.humidity.formatAsPercents()
            )
        }
        item {
            InfoColumn(
                name = R.string.visibility,
                value = details.visibility.formatAsDistance(context, details.unitsOfDistanceMeasure)
            )
        }
        item {
            InfoColumn(
                name = R.string.precipitation_probability,
                value = details.precipitationProb.formatAsPercents(0)
            )
        }
        item {
            InfoColumn(
                name = R.string.pressure,
                value = details.pressure.formatAsPressure(context)
            )
        }
    }
}

@Composable
private fun BottomBar(
    onNavigateToRoute: (String) -> Unit
) {
    OpenWeatherBottomBar(
        currentTab = WEATHER_FORECAST_DESTINATION,
        onNavigateToRoute = onNavigateToRoute
    )
}

@Composable
private fun getFakeWeatherForecasts(): List<WeatherForecast> {
    return PreviewData.getFakeDomainWeatherForecasts().map { it.toWeatherForecast() }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun WeatherForecastViewModel.weatherForecastState(): State<List<WeatherForecast>> {
    val context = LocalContext.current
    return produceState(
        initialValue = emptyList(),
        key1 = context
    ) {
        data.mapLatest { it.forecasts.map { domainForecast -> domainForecast.toWeatherForecast() } }
            .collectLatest {
                value = it
            }
    }
}

@Composable
private fun ErrorToast(state: WeatherForecastViewModel.State) {
    if (state is WeatherForecastViewModel.State.Error) {
        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_LONG).show()
    }
}

private fun shouldShowDate(prevForecast: MainInfo?, currForecast: MainInfo): Boolean {
    return currForecast.timestamp.get(Calendar.DAY_OF_MONTH) >
            (prevForecast?.timestamp?.get(Calendar.DAY_OF_MONTH) ?: Int.MAX_VALUE)
}

@Preview(
    showSystemUi = true,
    showBackground = true, fontScale = 1.0f
)
@Composable
private fun ContentPreview() {
    Content(
        forecasts = getFakeWeatherForecasts(),
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
        forecasts = emptyList(),
        isLoading = false,
        onRefresh = {}
    )
}
