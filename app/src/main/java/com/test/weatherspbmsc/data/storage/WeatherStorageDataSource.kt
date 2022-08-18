package com.test.weatherspbmsc.data.storage


import javax.inject.Inject

class WeatherStorageDataSource @Inject constructor (
    private val dao: WeatherDao
): WeatherDao by dao