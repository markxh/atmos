package za.co.dotmark.atmos.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import za.co.dotmark.atmos.WeatherApp
import za.co.dotmark.atmos.model.Forecast

@Database(entities = [WeatherEntity::class, ForecastEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun forecastDao(): ForecastDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase? {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        WeatherApp.instance,
                        AppDatabase::class.java, "app_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return instance
        }
    }
}