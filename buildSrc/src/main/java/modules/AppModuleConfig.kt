package modules

private const val API_KEY = "API_KEY"
private const val API_BASE_URL = "API_BASE_URL"
private const val API_AUTH_PARAM = "API_AUTH_PARAM"

object AppModuleConfig : BaseModuleConfig() {
    override val namespace = "ru.stolexiy.openweather"
    override val versionCode = 1
    override val versionName = "1.0.0"

    override val properties: Map<String, String> = mutableMapOf(
        API_BASE_URL to "http://api.openweathermap.org/data/2.5",
        API_KEY to "d9e6fe2ca9bd114df14262b014663852",
        API_AUTH_PARAM to "appid",
    )
}
