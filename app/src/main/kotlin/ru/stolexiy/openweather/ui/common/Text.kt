package ru.stolexiy.openweather.ui.common

import androidx.annotation.StringRes
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "with null text", showBackground = true
)
@Composable
private fun NullTextPreview() {
    TextWithDefaultValue(text = null)
}

@Preview(
    name = "with not null text", showBackground = true
)
@Composable
private fun NotNullTextPreview() {
    TextWithDefaultValue(text = "text")
}

@Composable
fun TextWithDefaultValue(
    modifier: Modifier = Modifier,
    text: String?,
    default: String = "—",
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        style = style,
        modifier = modifier,
        text = text ?: default
    )
}

@Composable
fun TextWithDefaultValue(
    modifier: Modifier = Modifier,
    @StringRes textId: Int?,
    default: String = "—",
    style: TextStyle = LocalTextStyle.current
) {
    if (textId == null)
        Text(
            style = style,
            modifier = modifier,
            text = default
        )
    else
        Text(
            style = style,
            modifier = modifier,
            text = stringResource(textId)
        )
}
