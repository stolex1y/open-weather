package ru.stolexiy.openweather.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.openweather.R

@Composable
fun InfoColumn(
    modifier: Modifier = Modifier,
    name: String,
    value: String? = null,
    defaultValue: String = "—",
    @DrawableRes image: Int? = null,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.then(modifier)
    ) {
        Text(text = name, style = MaterialTheme.typography.labelMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DrawableImage(resourceId = image, contentDescription = null)
            TextWithDefaultValue(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                default = defaultValue
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfosRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        content()
    }
}

@Composable
fun InfoColumn(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    value: String? = null,
    defaultValue: String = "—",
    @DrawableRes image: Int? = null,
) {
    InfoColumn(
        name = stringResource(id = name),
        modifier = modifier,
        value = value,
        defaultValue = defaultValue,
        image = image
    )
}

@Preview(showBackground = true)
@Composable
fun InfoColumnWithText() {
    InfoColumn(name = "Name", value = "Value", image = null)
}

@Preview(showBackground = true)
@Composable
fun InfoColumnWithImage() {
    InfoColumn(name = "Name", value = null, defaultValue = "", image = R.drawable.sun)
}

@Preview(showBackground = true)
@Composable
fun InfoColumnWithTextAndImage() {
    InfoColumn(name = "Name", value = "value", image = R.drawable.sun)
}
