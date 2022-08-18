package com.test.weatherspbmsc.data.rest

interface RestApi {

    suspend fun getDailyForecastByCityName(
        cityName: String,
        language: String
    ): CityResponses

    suspend fun getDailyForecast(
        longitude: Float,
        latitude: Float,
        days: Int,
        units: String,
        language: String
    ): WeatherResponse

}