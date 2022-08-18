package com.test.weatherspbmsc.features.favorities


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherspbmsc.data.storage.WeatherEntity
import com.test.weatherspbmsc.domain.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesCitiesViewModel @Inject constructor(
    val useCase: GetWeatherUseCase
): ViewModel() {


    private val _weather = MutableStateFlow<Event>(Event.Loading)
    val weather = _weather.asStateFlow()


    init {
        viewModelScope.launch {
            val retsult = Event.LoadedSuccess(useCase.invoke())

            _weather.emit(retsult)
        }
    }


    sealed class Event {

        object Loading : Event()

        data class LoadedSuccess(
            val value: List<WeatherEntity>
        ): Event()

        data class Error(
            val value: String
        )
    }
}