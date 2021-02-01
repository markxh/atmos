package za.co.dotmark.atmos.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import za.co.dotmark.atmos.model.CurrentWeather
import za.co.dotmark.atmos.model.Forecast
import za.co.dotmark.atmos.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel : ViewModel() {

    var cityName = MutableLiveData("")
    var currentTemp = MutableLiveData(0)
    var maxTemp = MutableLiveData(0)
    var minTemp = MutableLiveData(0)
    var description = MutableLiveData("")
    var weatherId = MutableLiveData(0)
    var forecastList = MutableLiveData<List<CurrentWeather>>()

    var isLoading = MutableLiveData(false)

    private val weatherRepository: WeatherRepository = WeatherRepository.getInstance()

    var units = "metric"

    fun refreshWeather(location: Location) {
        isLoading.value = true

        getWeatherByLocation(location)
        getForecast(location)
    }

    fun getWeatherByLocation(location: Location) {
        weatherRepository.getWeather(location.latitude, location.longitude, units) {response, error ->
            if(response != null) {
                updateCurrentWeather(response)
            } else {
                //todo show error
            }

            isLoading.value = false
        }
    }

    fun getForecast(location: Location) {
        weatherRepository.getForecast(location.latitude, location.longitude, units) {response, error ->
            if(response != null) {
                updateForecast(response)
            } else {
                //todo show error
            }
        }

        isLoading.value = false
    }

    private fun updateCurrentWeather(response: CurrentWeather) {
        try {
            cityName.value = response.name
            currentTemp.value = response.main?.temp?.toInt()
            maxTemp.value = response.main?.tempMax?.toInt()
            minTemp.value = response.main?.tempMin?.toInt()
            description.value = response.weather?.get(0)?.main
            weatherId.value = response.weather?.get(0)?.id
        } catch (e: Exception) {
            println("error: ${e.localizedMessage}")
        }
    }

    private fun updateForecast(response: Forecast) {
        val items = ArrayList<CurrentWeather>()

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())

        var dayName = ""

        response.list?.forEach {

            val date = formatter.parse(it.dt_txt)
            val day = dayFormatter.format(date)

            if(dayName != day) {
                dayName = day
                items.add(it)
            }
        }

        forecastList.value = items
    }
}