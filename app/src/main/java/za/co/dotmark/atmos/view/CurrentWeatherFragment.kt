package za.co.dotmark.atmos.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.current_weather_fragment.*
import za.co.dotmark.atmos.R
import za.co.dotmark.atmos.databinding.CurrentWeatherFragmentBinding
import za.co.dotmark.atmos.viewmodel.WeatherViewModel

class CurrentWeatherFragment : Fragment() {

    private val LOCATION_REQUEST_CODE = 99

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var forecastAdapter: ForecastAdapter

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: CurrentWeatherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.current_weather_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel

        fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        addLocationListener()

        forecastAdapter = context?.let { ForecastAdapter(it) }!!

        binding.forecastList.adapter = forecastAdapter

        viewModel.forecastList.observe(this.viewLifecycleOwner, Observer { forecastAdapter.updateForecast(it) })
        viewModel.weatherId.observe(this.viewLifecycleOwner, Observer { if(it != 0) updateBackground(it) })
    }

    private fun updateBackground(id: Int) {
        val iconId : Int
        val gradientId : Int
        val bgColorId : Int

        when {
            id == 800 -> {
                iconId = R.drawable.forest_sunny
                bgColorId = R.color.sunny
                gradientId = R.drawable.gradient_sunny
            }
            id > 800 -> {
                iconId = R.drawable.forest_cloudy
                bgColorId = R.color.cloudy
                gradientId = R.drawable.gradient_cloudy
            }
            else -> {
                iconId = R.drawable.forest_rainy
                bgColorId = R.color.rainy
                gradientId = R.drawable.gradient_rainy
            }
        }

        Glide
            .with(this)
            .load(iconId)
            .centerCrop()
            .placeholder(R.drawable.forest_sunny)
            .into(background)

        try {
            binding.forecastList.setBackgroundResource(bgColorId)
            binding.dayTempContainer.setBackgroundResource(gradientId)
        } catch (e: Exception) {
            println("error: ${e.localizedMessage}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_REQUEST_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addLocationListener()
            }
        }
    }

    private fun addLocationListener() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST_CODE)

            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener(viewModel::refreshWeather)
    }
}