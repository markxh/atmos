package za.co.dotmark.atmos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import za.co.dotmark.atmos.model.CurrentWeather

@Entity(tableName = "weather_table")
class WeatherEntity(weather: CurrentWeather) {

    @PrimaryKey
    var id = weather.id

    var weatherData: String = Gson().toJson(weather)

    fun getWeather() : CurrentWeather? {
        return try {
            Gson().fromJson(weatherData, CurrentWeather::class.java)
        } catch (e: Exception) {
            null
        }
    }

    constructor() : this(CurrentWeather())
}