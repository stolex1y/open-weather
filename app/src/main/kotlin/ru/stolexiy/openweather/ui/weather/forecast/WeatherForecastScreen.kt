package ru.stolexiy.openweather.ui.weather.forecast

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.ui.common.OpenWeatherTopAppBar
import ru.stolexiy.openweather.ui.common.PullToRefresh
import ru.stolexiy.openweather.ui.navigation.OpenWeatherBottomBar
import ru.stolexiy.openweather.ui.util.preview.PreviewData
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
                forecasts = forecasts
            )
        }
    }
}

@Composable
private fun ForecastsRow(
    modifier: Modifier,
    forecasts: List<WeatherForecast>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(30.dp),
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
                shouldShowDate(prevForecast = prev, currForecast = item)
            )
        }
    }
}

@Composable
private fun ForecastCard(
    forecast: WeatherForecast,
    showDate: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = forecast.timeStr, style = MaterialTheme.typography.titleSmall)
        Text(
            text = if (showDate) forecast.dateStr else "",
            style = MaterialTheme.typography.bodySmall
        )
        Image(
            painter = painterResource(id = forecast.weatherGroupIcon),
            contentDescription = stringResource(
                id = forecast.weatherGroupLabel
            )
        )
        Text(text = forecast.tempStr, style = MaterialTheme.typography.headlineMedium)
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

private fun shouldShowDate(prevForecast: WeatherForecast?, currForecast: WeatherForecast): Boolean {
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
