package ru.stolexiy.openweather.data.remote.model

interface WeatherDto {
    var main: WeatherMainParametersDto
    var visibility: Int?
    var clouds: CloudsDto?
    var wind: WindDto?
    var timestamp: Long?
}
