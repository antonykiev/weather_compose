package com.test.weatherspbmsc.data.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val httpClient: HttpClient): RestApi {

    private val apiKey = "34115a521674c576d9eb6207e157f776"
    private val serverHost = "api.openweathermap.org"

    override suspend fun getDailyForecastByCityName(
        cityName: String,
        language: String

    ): CityResponses {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = serverHost
                path("data/2.5/weather")

                parameters.append("q", cityName)
                parameters.append("appid", apiKey)
                parameters.append("lang", language)
            }
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getDailyForecast(
        longitude: Float,
        latitude: Float,
        days: Int,
        units: String,
        language: String
    ): WeatherResponse {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = serverHost
                path("data/2.5/forecast/daily")

                parameters.append("lat", latitude.toString())
                parameters.append("lon", longitude.toString())
                parameters.append("cnt", days.toString())
                parameters.append("units", units)
                parameters.append("lang", language)

                parameters.append("appid", apiKey)

            }
            contentType(ContentType.Application.Json)
        }
    }
}