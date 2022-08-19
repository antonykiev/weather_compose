package com.test.weatherspbmsc.domain

import com.test.weatherspbmsc.data.rest.RestApi
import com.test.weatherspbmsc.data.rest.WeatherLanguage
import com.test.weatherspbmsc.data.rest.WeatherResponse
import com.test.weatherspbmsc.data.rest.WeatherUnits
import com.test.weatherspbmsc.data.storage.WeatherEntity
import javax.inject.Inject

class GetMapWeather @Inject constructor(
    private val api: RestApi
) {

    suspend operator fun invoke(lon: Float, lat: Float): WeatherResponse{
        return api.getDailyForecast(
            longitude = lon,
            latitude = lat,
            language = WeatherLanguage.Russian.value,
            units = WeatherUnits.metric.name,
            days = 1
        )
    }
}