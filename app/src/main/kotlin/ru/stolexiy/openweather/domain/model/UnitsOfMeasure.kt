package ru.stolexiy.openweather.domain.model

enum class UnitsOfMeasure(
    val tempMeasure: UnitsOfTempMeasure,
    val windMeasure: UnitsOfWindMeasure,
    val distanceMeasure: UnitsOfDistanceMeasure,
) {
    METRIC(
        UnitsOfTempMeasure.CELSIUS,
        UnitsOfWindMeasure.METERS_PER_SECOND,
        UnitsOfDistanceMeasure.METERS
    ),
    STANDARD(
        UnitsOfTempMeasure.KELVIN,
        UnitsOfWindMeasure.METERS_PER_SECOND,
        UnitsOfDistanceMeasure.METERS
    ),
    IMPERIAL(
        UnitsOfTempMeasure.FAHRENHEIT,
        UnitsOfWindMeasure.MILES_PER_HOUR,
        UnitsOfDistanceMeasure.MILES
    )
}