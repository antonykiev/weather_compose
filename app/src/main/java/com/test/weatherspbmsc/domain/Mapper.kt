package com.test.weatherspbmsc.domain

import com.test.weatherspbmsc.data.rest.WeatherResponse
import com.test.weatherspbmsc.data.storage.WeatherEntity


fun WeatherResponse.toEntityLis(
    cityName: String,
    latitude: Float,
    longitude: Float
) : List<WeatherEntity> {
    return  weatherList.map { forecast ->
        WeatherEntity(
            cityName = cityName,
            latitude = latitude,
            longitude = longitude,
            date = forecast.date,
            dayTemperature = forecast.temperature.day,
            description = forecast.weather.first().description,
            icon = forecast.weather.first().icon
        )
    }

}

