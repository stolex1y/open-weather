package ru.stolexiy.openweather.domain.model

import java.util.Calendar

data class DomainWeatherDetails(
    override val location: DomainLocation,
    override val temperature: DomainTemperature,
    override val pressure: Int,
    override val humidity: Int,
    override val clouds: Int,
    override val precipitation: DomainPrecipitation,
    override val wind: DomainWind,
    override val timestamp: Calendar,
    override val visibility: Int,
    override val weatherGroup: WeatherGroup,
    override val unitsOfMeasure: UnitsOfMeasure = UnitsOfMeasure.METRIC
) : DomainWeather {
}
