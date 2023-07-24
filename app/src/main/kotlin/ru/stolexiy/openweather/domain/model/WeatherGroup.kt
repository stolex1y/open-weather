package ru.stolexiy.openweather.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.stolexiy.openweather.R

enum class WeatherGroup(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
) {
    THUNDERSTORM(R.drawable.storm, R.string.thunderstorm),
    RAIN(R.drawable.rainy, R.string.rain),
    SNOW(R.drawable.snowy, R.string.snow),
    CLOUDY(R.drawable.cloudy, R.string.cloudy),
    CLEAR_DAY(R.drawable.sunny, R.string.clear),
    CLEAR_NIGHT(R.drawable.moon, R.string.clear),
    MIST(R.drawable.foggy, R.string.mist),
}
