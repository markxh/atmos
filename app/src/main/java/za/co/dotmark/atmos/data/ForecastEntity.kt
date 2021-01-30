package za.co.dotmark.atmos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import za.co.dotmark.atmos.model.Forecast
import java.lang.Exception

@Entity(tableName = "forecast_table")
class ForecastEntity(forecast: Forecast) {

    @PrimaryKey
    var id = forecast.city?.id

    var forecastData: String = Gson().toJson(forecast)

    fun getForecast() : Forecast? {
        return try {
            Gson().fromJson(forecastData, Forecast::class.java)
        } catch (e: Exception) {
            null
        }
    }
    
    constructor() : this(Forecast())
}