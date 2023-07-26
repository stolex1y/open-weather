package ru.stolexiy.openweather.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.openweather.R

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun OpenWeatherCardPreview() {
    OpenWeatherCard(
        modifier = Modifier
            .size(width = 200.dp, height = 100.dp)
            .padding(100.dp)
    ) {
        Text("Text")
    }
}

@Composable
fun OpenWeatherCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
            .shadow(
                elevation = 25.dp,
            )
    ) {
        content()
    }
}

@Composable
fun MainInfoCard(
    modifier: Modifier = Modifier,
    date: String?,
    time: String?,
    temperature: String?,
    @DrawableRes weatherGroupIcon: Int?,
    @StringRes weatherGroupLabel: Int?,
    itemsInRow: Int = 3,
    infoColumns: LazyGridScope.() -> Unit
) {
    OpenWeatherCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            DateTimeRow(
                modifier = Modifier.fillMaxWidth(),
                date = date,
                time = time
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextWithDefaultValue(
                    text = temperature,
                    style = MaterialTheme.typography.displayLarge
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    DrawableImage(
                        resourceId = weatherGroupIcon,
                        contentDescription = null
                    )
                    TextWithDefaultValue(
                        textId = weatherGroupLabel,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            InfosRow(
                itemsInRow = itemsInRow
            ) {
                infoColumns()
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun MainInfoCardPreview() {
    MainInfoCard(
        modifier = Modifier.fillMaxWidth(),
        date = "20.04.2023",
        time = "20:04",
        temperature = "+20C",
        weatherGroupIcon = R.drawable.sun,
        weatherGroupLabel = R.string.clear,
        itemsInRow = 3,
    ) {
        item {
            InfoColumn(name = R.string.humidity, value = "55%")
        }
        item {
            InfoColumn(name = R.string.humidity, value = "55%")
        }
        item {
            InfoColumn(name = R.string.humidity, value = "55%")
        }
        item {
            InfoColumn(
                modifier = Modifier.padding(top = 20.dp),
                name = R.string.humidity,
                value = "55%"
            )
        }
    }
}
