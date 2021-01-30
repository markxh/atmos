package za.co.dotmark.atmos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast_table")
    fun load(): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(forecastEntity: ForecastEntity)
}