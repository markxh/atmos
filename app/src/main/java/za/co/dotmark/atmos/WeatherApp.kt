package za.co.dotmark.atmos

import android.app.Application

class WeatherApp : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: WeatherApp
            private set
    }
}