package com.test.weatherspbmsc.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test.weatherspbmsc.BottomBarScreen
import com.test.weatherspbmsc.features.ScreenContent
import com.test.weatherspbmsc.features.favorities.FavoritesCitiesViewModel
import com.test.weatherspbmsc.features.favorities.FavoritesLoading
import com.test.weatherspbmsc.features.map.MapScreenContent

@Composable
fun FavoritesNavGraph(
    viewModel: FavoritesCitiesViewModel = hiltViewModel(),
    navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        route = Graph.FAVORITES,
        startDestination = BottomBarScreen.Favorities.route
    ) {

        composable(route = BottomBarScreen.Favorities.route) {
            FavoritesLoading(
                viewModel = viewModel
            )
        }

        composable(route = BottomBarScreen.Map.route) {
            MapScreenContent(

            )
        }

    }

}