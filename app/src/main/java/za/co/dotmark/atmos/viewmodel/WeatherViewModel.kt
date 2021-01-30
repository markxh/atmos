package za.co.dotmark.atmos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import za.co.dotmark.atmos.model.CurrentWeather
import za.co.dotmark.atmos.model.Forecast
import za.co.dotmark.atmos.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    var cityName = MutableLiveData("")
    var currentTemp = MutableLiveData(0)
    var maxTemp = MutableLiveData(0)
    var minTemp = MutableLiveData(0)
    var description = MutableLiveData("")
    var weatherId = MutableLiveData(0)
    var forecastList = MutableLiveData<List<CurrentWeather>>()

    private val weatherRepository: WeatherRepository = WeatherRepository.getInstance()

    var units = "metric"

    //Cape Town
    var lat = 33.9249
    var long = 18.4241

    fun refreshWeather() {
        getWeatherByLocation()
        getForecast()
    }

    fun getWeatherByLocation() {
        weatherRepository.getWeather(lat, long, units) {response, error ->
            if(response != null) {
                updateCurrentWeather(response)
            } else {
                //todo show error
            }
        }
    }

    fun getForecast() {
        weatherRepository.getForecast(lat, long, units) {response, error ->
            if(response != null) {
                updateForecast(response)
            } else {
                //todo show error
            }
        }
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
        forecastList.value = response.list
    }
}