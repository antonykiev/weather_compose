package com.test.weatherspbmsc.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.test.weatherspbmsc.BottomBarScreen
import com.test.weatherspbmsc.features.ScreenContent
import com.test.weatherspbmsc.features.favorities.FavoritesContent

@Composable
fun FavoritesNavGraph(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        route = Graph.FAVORITES,
        startDestination = BottomBarScreen.Favorities.route
    ) {

        composable(route = BottomBarScreen.Favorities.route) {
            FavoritesContent(

            )
        }

        composable(route = BottomBarScreen.Map.route) {
            ScreenContent(
                name = BottomBarScreen.Map.route,
            )
        }

    }

}