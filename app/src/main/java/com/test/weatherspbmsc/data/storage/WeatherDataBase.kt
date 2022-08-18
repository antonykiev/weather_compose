package com.test.weatherspbmsc.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WeatherEntity::class,
    ],
    version = 1
)
abstract class WeatherDataBase: RoomDatabase() {

    abstract val weatherDao: WeatherDao

    companion object {
        const val DB_NAME = "weather_app"
    }

}