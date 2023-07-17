package ru.stolexiy.openweather.domain.model

import androidx.annotation.StringRes
import ru.stolexiy.openweather.R

enum class UnitsOfDistanceMeasure(@StringRes val sign: Int) {
    METERS(R.string.meters),
    MILES(R.string.miles)
}
