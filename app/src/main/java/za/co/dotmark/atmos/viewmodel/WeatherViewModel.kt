package za.co.dotmark.atmos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import za.co.dotmark.atmos.model.CurrentWeather
import za.co.dotmark.atmos.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    var cityName = MutableLiveData("")
    var currentTemp = MutableLiveData(0)
    var maxTemp = MutableLiveData(0)
    var minTemp = MutableLiveData(0)

    var weatherIcon = MutableLiveData("")

    private val weatherRepository: WeatherRepository = WeatherRepository.getInstance()

    //Cape Town
    var lat = 33.9249
    var long = 18.4241

    var name = "Cape Town"

    var units = "metric"

    fun refreshWeather() {
        getWeatherByCityName()
    }

    fun getWeatherByCoords() {
        weatherRepository.getWeather(lat, long, units) {response, error ->
            if(response != null) {
                update(response)
            } else {
                //todo show error
            }
        }
    }

    fun getWeatherByCityName() {
        weatherRepository.GetWeatherByCityName(name, units) {response, error ->
            if(response != null) {
                update(response)
            } else {
                //todo show error
            }
        }
    }

    private fun update(response: CurrentWeather) {
        try {
            cityName.value = response.name
            currentTemp.value = response.main?.temp?.toInt()
            maxTemp.value = response.main?.tempMax?.toInt()
            minTemp.value = response.main?.tempMin?.toInt()
            weatherIcon.value = response.weather?.get(0)?.icon
        } catch (e: Exception) {
            println("error: ${e.localizedMessage}")
        }
    }
}