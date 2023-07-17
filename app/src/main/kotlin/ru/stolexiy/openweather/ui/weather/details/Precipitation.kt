package ru.stolexiy.openweather.ui.weather.details

import android.content.Context
import androidx.annotation.DrawableRes
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.domain.model.DomainPrecipitation
import ru.stolexiy.openweather.ui.util.Formatters.formatAsPrecipitation

class Precipitation(
    rain1h: String?,
    rain3h: String?,
    snow1h: String?,
    snow3h: String?,
) {
    val hasSnow: Boolean = snow1h != null || snow3h != null
    val hasRain: Boolean = rain1h != null || rain3h != null
    val hasPrecipitation: Boolean = hasSnow || hasRain
    val per1h: String? = rain1h ?: snow1h
    val per3h: String? = rain3h ?: snow3h

    @DrawableRes
    val icon: Int? = run {
        if (hasRain)
            R.drawable.rainy
        else if (hasSnow)
            R.drawable.snowy
        else
            null
    }
}

fun DomainPrecipitation.toPrecipitation(context: Context) = Precipitation(
    rain1h = rain1h?.formatAsPrecipitation(context),
    rain3h = rain3h?.formatAsPrecipitation(context),
    snow1h = snow1h?.formatAsPrecipitation(context),
    snow3h = snow3h?.formatAsPrecipitation(context),
)
