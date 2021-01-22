package za.co.dotmark.atmos.model

import com.google.gson.annotations.SerializedName

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
    val id: Long? = null,
    val name: String? = null)

data class Weather (val id: Long,
                    val main: String,
                    val description: String,
                    val icon: String)

data class Clouds (
    val all: Long
)

data class Coordinates (
    val lon: Double,
    val lat: Double)

data class Main (
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    val pressure: Long,

    val humidity: Long
)

data class Rain (
    @SerializedName("1h")
    val the1H: Double
)

data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Wind (
    val speed: Double,
    val deg: Long,
    val gust: Double
)