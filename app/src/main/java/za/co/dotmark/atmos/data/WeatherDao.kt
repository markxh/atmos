package za.co.dotmark.atmos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    fun load(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weatherEntity: WeatherEntity)
}