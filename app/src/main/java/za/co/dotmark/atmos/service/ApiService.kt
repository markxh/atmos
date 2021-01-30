package za.co.dotmark.atmos.service

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import za.co.dotmark.atmos.model.CurrentWeather
import za.co.dotmark.atmos.model.Forecast
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("2.5/weather")
    fun getLocationWeather(@Query("lat") lat: Double,
                           @Query("lon") long: Double,
                           @Query("units") units: String,
                           @Query("appid") appid: String) : Call<CurrentWeather>

    @GET("2.5/weather")
    fun getWeatherByCityName(@Query("q") name: String,
                           @Query("units") units: String,
                           @Query("appid") appid: String) : Call<CurrentWeather>

    @GET("2.5/forecast")
    fun get5DayForecast(@Query("lat") lat: Double,
                        @Query("lon") long: Double,
                        @Query("units") units: String,
                        @Query("cnt") cnt: Int,
                        @Query("appid") appid: String) : Call<Forecast>

    companion object {

        fun create() : ApiService {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)
        }
    }
}