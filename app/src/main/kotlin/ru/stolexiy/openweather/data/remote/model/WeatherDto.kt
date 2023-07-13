package ru.stolexiy.openweather.data.remote.model

interface WeatherDto {
    var main: WeatherMainParametersDto
    var clouds: CloudsDto
    var wind: WindDto
    var timestamp: Long?
}
