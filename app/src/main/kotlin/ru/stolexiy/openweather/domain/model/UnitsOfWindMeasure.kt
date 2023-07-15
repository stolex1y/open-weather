package ru.stolexiy.openweather.domain.model

import androidx.annotation.StringRes
import ru.stolexiy.openweather.R

enum class UnitsOfWindMeasure(@StringRes val sign: Int) {
    METERS_PER_SECOND(R.string.meters_per_second),
    MILES_PER_HOUR(R.string.miles_per_hour)
}
