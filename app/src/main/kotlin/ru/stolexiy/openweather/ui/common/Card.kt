package ru.stolexiy.openweather.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                spotColor = Color(0x26001454),
                ambientColor = Color(0x26001454)
            )
    ) {
        content()
    }
}
