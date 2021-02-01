package za.co.dotmark.atmos.model

data class CurrentWeather(
    val coord: Coordinates? = null,
    val weather: List<Weather>? = null,
    val main: Main? = null,
    val visibility: Long? = null,
    val wind: Wind? = null,
    val rain: Rain? = null,
    val clouds: Clouds? = null,
    val dt: Long? = null,
    val sys: Sys? = null,
    val timezone: Long? = null,
    val id: Int? = null,
    val name: String? = null,
    val dt_txt: String? = null)

