package com.test.weatherspbmsc.features.favorities


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherspbmsc.data.storage.WeatherEntity
import com.test.weatherspbmsc.domain.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesCitiesViewModel @Inject constructor(
    private val useCase: GetWeatherUseCase
): ViewModel() {

    private val _weather = MutableStateFlow<Event>(Event.Loading)
    val weather = _weather.asStateFlow()

    init {
        viewModelScope.launch {

            try {
                val resultList: List<WeatherEntity> = withContext(Dispatchers.IO) {
                    useCase.invoke()
                }
                val resultMap: Map<String, List<WeatherEntity>> = withContext(Dispatchers.Default) {
                    val keys: List<String> = resultList.associateBy { it.cityName }.map { it.key }
                    return@withContext keys.map { key ->  key to resultList.filter { it.cityName == key } }
                        .toMap()
                }

                _weather.emit(
                    Event.LoadedSuccess(resultMap)
                )
            } catch (e: Exception) {
                _weather.emit(
                    Event.Error(e.localizedMessage ?: "")
                )
            }
        }
    }


    sealed class Event {

        object Loading : Event()

        data class LoadedSuccess(
            val value: Map<String, List<WeatherEntity>>
        ) : Event()

        data class Error(
            val value: String
        ) : Event()
    }
}