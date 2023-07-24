package ru.stolexiy.openweather.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.stolexiy.openweather.R

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun OpenWeatherTopAppBarPreview() {
    OpenWeatherTopAppBar(title = "Preview", onRefresh = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenWeatherTopAppBar(
    title: String,
    onRefresh: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        modifier = Modifier.statusBarsPadding(),
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title)
                IconButton(onClick = onRefresh) {
                    Icon(
                        painter = painterResource(id = R.drawable.restart),
                        contentDescription = stringResource(id = R.string.refresh)
                    )
                }
            }
        }
    )
}
