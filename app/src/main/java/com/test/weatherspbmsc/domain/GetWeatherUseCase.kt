package com.test.weatherspbmsc.domain

import com.test.weatherspbmsc.data.rest.RestApi
import com.test.weatherspbmsc.data.rest.WeatherLanguage
import com.test.weatherspbmsc.data.rest.WeatherUnits
import com.test.weatherspbmsc.data.storage.WeatherDao
import com.test.weatherspbmsc.data.storage.WeatherEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.util.*
//import com.test.weatherspbmsc.data.storage.WeatherDao
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    val storage: WeatherDao,
    val api: RestApi
) {

    private val favoritesCities = listOf("Moscow", "Saint Petersburg")
    private val validTimeDiff = 60 * 60 * 60

    suspend operator fun invoke(): List<WeatherEntity> {

        val storageWeatherList = runCatching {
            storage.getAllEntities().first()
        }.getOrElse { emptyList() }

        val storageTimeStamp = runCatching { storageWeatherList.first().timestamp }.getOrElse { 0 }
        val currentTimeStamp = System.currentTimeMillis() / 1000
        val timestampDiff = currentTimeStamp - storageTimeStamp
        if (timestampDiff < validTimeDiff) {
            return storageWeatherList
        } else {
            val result = favoritesCities.map { cityName ->
                val cityData = api.getDailyForecastByCityName(
                    cityName,
                language = WeatherLanguage.Russian.value,
            )
                val weatherRemote = api.getDailyForecast(
                    longitude = cityData.coordinates.longitude,
                    latitude = cityData.coordinates.latitude,
                    days = 16,
                    language = WeatherLanguage.Russian.value,
                    units = WeatherUnits.metric.name
                )

                val weather = weatherRemote.toEntityLis(
                    cityData.name,
                    cityData.coordinates.latitude,
                    cityData.coordinates.longitude
                )
                return@map weather
            }.flatten()
                .also { updateStorage(it) }

            return result
        }
    }

    private suspend fun updateStorage(list: List<WeatherEntity>) {
        storage.deleteAll()
        storage.insert(list)
    }
}