package com.test.weatherspbmsc.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun insert(entities: List<WeatherEntity>)

    @Query("SELECT * FROM weather_entity order by date asc")
    fun getAllEntities(): Flow<List<WeatherEntity>>

    @Query("DELETE FROM weather_entity")
    suspend fun deleteAll()
}