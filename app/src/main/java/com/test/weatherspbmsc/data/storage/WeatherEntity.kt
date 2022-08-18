package com.test.weatherspbmsc.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather_entity")
data class WeatherEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val cityName: String,
    val date: Int,
    val latitude: Float,
    val longitude: Float,
    val icon: String,
    val description: String,
    val dayTemperature: String,
    val timestamp: Long = System.currentTimeMillis() / 1000,

)