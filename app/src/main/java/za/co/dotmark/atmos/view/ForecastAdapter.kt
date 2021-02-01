package za.co.dotmark.atmos.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import za.co.dotmark.atmos.R
import za.co.dotmark.atmos.model.CurrentWeather
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var forecast = listOf<CurrentWeather>()

    fun updateForecast(forecast: List<CurrentWeather>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ForecastItemViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val weatherItem = forecast[position]

            (holder as ForecastItemViewHolder).day.text = weatherItem.dt_txt?.let { getDay(it) }
            holder.temp.text = context.getString(R.string.degrees, weatherItem.main?.temp?.toInt().toString())

            //todo add more icons for all weather conditions
            val iconId: Int = when {
                weatherItem.weather?.get(0)!!.id == 800 -> R.drawable.clear
                weatherItem.weather.get(0).id > 800 -> R.drawable.partlysunny
                else -> R.drawable.rain
            }

            Glide
                .with(context)
                .load(iconId)
                .into(holder.icon)
        } catch (e: Exception) {
            println("error: ${e.localizedMessage}")
        }
    }

    private fun getDay(dateString: String) : String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.parse(dateString)

        val dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return dayFormatter.format(date)
    }

    private class ForecastItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.view_forecast_item, parent, false)) {

        var day = itemView.findViewById<TextView>(R.id.day)
        var icon = itemView.findViewById<ImageView>(R.id.weather_icon)
        var temp = itemView.findViewById<TextView>(R.id.temp)
    }
}