package za.co.dotmark.atmos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import za.co.dotmark.atmos.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CurrentWeatherFragment.newInstance())
                    .commitNow()
        }
    }
}