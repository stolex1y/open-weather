package ru.stolexiy.openweather.domain.model

enum class UnitsOfMeasure(
    val tempMeasure: UnitsOfTempMeasure,
    val windMeasure: UnitsOfWindMeasure
) {
    METRIC(UnitsOfTempMeasure.CELSIUS, UnitsOfWindMeasure.METERS_PER_SECOND),
    STANDARD(UnitsOfTempMeasure.KELVIN, UnitsOfWindMeasure.METERS_PER_SECOND),
    IMPERIAL(UnitsOfTempMeasure.FAHRENHEIT, UnitsOfWindMeasure.MILES_PER_HOUR)
}