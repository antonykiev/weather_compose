package com.test.weatherspbmsc.features.map

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.test.weatherspbmsc.features.favorities.WeatherIndicator

private const val TAG = "BasicMapActivity"

private val point = LatLng(46.23, 14.52)
private val defaultCameraPosition = CameraPosition.fromLatLngZoom(point, 9f)


@Composable
fun MapScreenContent(
    viewModel: MapWeatherViewModel = hiltViewModel()
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMapView(
            viewModel = viewModel,
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            },
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
fun GoogleMapView(
    viewModel: MapWeatherViewModel,
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
) {

    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var mapVisible by remember { mutableStateOf(true) }

    val resultWeather = remember { viewModel.weatherState }


    if (mapVisible) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLoaded = onMapLoaded,
            onPOIClick = {
                Log.d(TAG, "POI clicked: ${it.name}")
            }
        )

    }
        Column(
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {

            when (val it = resultWeather.value) {
                is MapWeatherViewModel.Event.Error -> {

                }
                is MapWeatherViewModel.Event.LoadedSuccess -> {
                    WeatherIndicator(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(16.dp),
                        temperature = it.value.weatherList.first().temperature.day,
                        imageHeight = 60.dp,
                        weatherIcon = it.value.weatherList.first().weather.first().icon
                    )
                }
                MapWeatherViewModel.Event.Loading -> {
                    Text(text = "Loading...")
                }
            }
            if (cameraPositionState.isMoving) {
                viewModel.loadWeather(
                    lat = cameraPositionState.position.target.latitude.toFloat(),
                    lon = cameraPositionState.position.target.longitude.toFloat()
                )
            }
        }
}


