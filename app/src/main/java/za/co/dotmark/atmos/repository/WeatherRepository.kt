package za.co.dotmark.atmos.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import za.co.dotmark.atmos.BuildConfig
import za.co.dotmark.atmos.data.*
import za.co.dotmark.atmos.model.CurrentWeather
import za.co.dotmark.atmos.model.Forecast
import za.co.dotmark.atmos.service.ApiService

class WeatherRepository {

    lateinit var weatherDao: WeatherDao
    lateinit var forecastDao: ForecastDao

    init {
        try {
            val db = AppDatabase.getInstance()!!
            weatherDao = db.weatherDao()
            forecastDao = db.forecastDao()
        } catch (e: Exception) {
            println("error: ${e.localizedMessage}")
        }
    }

    private val apiService by lazy {
        ApiService.create()
    }

    fun getWeather(lat: Double, long: Double, units: String, onResult: (response: CurrentWeather?, error: ResponseBody?) -> Unit) {
        apiService.getLocationWeather(lat, long, units, BuildConfig.API_KEY).enqueue(object: Callback<CurrentWeather> {
                override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                    if(response.isSuccessful) {
                        onResult(response.body(), response.errorBody())

                        try {
                            GlobalScope.launch {
                                response.body()?.let { WeatherEntity(it) }?.let { weatherDao.save(it) }
                            }
                        } catch (e: Exception) {
                            println("error: ${e.localizedMessage}")
                        }
                    } else {
                        //todo try get from room
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    println("error: ${t.localizedMessage}")
                    onResult(null, null)
                }
            })
    }

    fun getForecast(lat: Double, long: Double, units: String, onResult: (response: Forecast?, error: ResponseBody?) -> Unit) {
        apiService.get5DayForecast(lat, long, units, BuildConfig.API_KEY).enqueue(object: Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful) {
                    onResult(response.body(), response.errorBody())

                    try {
                        GlobalScope.launch {
                            response.body()?.let { ForecastEntity(it) }
                                ?.let { forecastDao.save(it) }
                        }
                    } catch (e: Exception) {
                        println("error: ${e.localizedMessage}")
                    }
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    companion object {
        @Volatile private var instance: WeatherRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: WeatherRepository().also { instance = it }
        }
    }
}