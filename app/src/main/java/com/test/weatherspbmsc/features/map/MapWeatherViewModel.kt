package com.test.weatherspbmsc.features.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherspbmsc.data.rest.WeatherResponse
import com.test.weatherspbmsc.data.storage.WeatherEntity
import com.test.weatherspbmsc.domain.GetMapWeather
import com.test.weatherspbmsc.features.favorities.FavoritesCitiesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapWeatherViewModel @Inject constructor(
    private val useCase: GetMapWeather
): ViewModel() {

    private var job: Job? = null

    private val _weatherState = mutableStateOf<Event>(Event.Loading)
    val weatherState: State<Event> = _weatherState

    fun loadWeather(lat: Float, lon: Float) {
        viewModelScope.launch {
            job?.cancelAndJoin()
            job = launch(Dispatchers.IO) {
                val result = runCatching { useCase.invoke(lat, lon) }.getOrNull()
                result?.let {
                    _weatherState.value = Event.LoadedSuccess(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (job?.isCancelled == true)
            job?.cancel()
    }

    sealed class Event {
        object Loading : Event()

        data class LoadedSuccess(
            val value: WeatherResponse
        ) : Event()

        data class Error(
            val value: String
        ) : Event()

    }
}