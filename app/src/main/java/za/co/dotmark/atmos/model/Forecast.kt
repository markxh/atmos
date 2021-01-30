package za.co.dotmark.atmos.model

data class Forecast(
    val message: String? = null,
    val city: City? = null,
    val list: List<CurrentWeather>? = null)

