package com.test.weatherspbmsc.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponses(
    @SerialName("coord")
    val coordinates: Coordinates,
    @SerialName("name")
    val name: String
)

@Serializable
data class WeatherResponse(
    @SerialName("list")
    val weatherList: List<ForecastItem>
)

@Serializable
data class Coordinates(
    @SerialName("lon")
    val longitude: Float,
    @SerialName("lat")
    val latitude: Float
)

@Serializable
data class ForecastItem(
    @SerialName("dt")
    val date: Int,
    @SerialName("temp")
    val temperature: Temperature,
    @SerialName("weather")
    val weather: List<Weather>
)

@Serializable
data class Temperature(
    @SerialName("day")
    val day: Float
)

@Serializable
data class Weather(
    @SerialName("icon")
    val icon: String,
    @SerialName("description")
    val description: String
)
