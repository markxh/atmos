package za.co.dotmark.atmos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.current_weather_fragment.*
import za.co.dotmark.atmos.R
import za.co.dotmark.atmos.databinding.CurrentWeatherFragmentBinding
import za.co.dotmark.atmos.viewmodel.WeatherViewModel

class CurrentWeatherFragment : Fragment() {

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

        forecastAdapter = context?.let { ForecastAdapter(it) }!!
        viewModel.forecastList.observe(this.viewLifecycleOwner, Observer { forecastAdapter.updateForecast(it) })

        viewModel.weatherId.observe(this.viewLifecycleOwner, Observer {

            val iconId : Int = when {
                it == 800 -> R.drawable.forest_sunny
                it > 800 -> R.drawable.forest_cloudy
                else -> R.drawable.forest_rainy
            }

            Glide
                .with(this)
                .load(iconId)
                .centerCrop()
                .into(background)
        })
    }

    override fun onResume() {
        super.onResume()

        //todo check location
        //todo show loading indicator
        viewModel.refreshWeather()
    }
}