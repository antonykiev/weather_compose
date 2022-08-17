package com.test.weatherspbmsc.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.test.weatherspbmsc.BottomBarScreen
import com.test.weatherspbmsc.features.ScreenContent

@Composable
fun FavoritesNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.FAVORITES,
        startDestination = BottomBarScreen.Favorities.route
    ) {
        composable(route = BottomBarScreen.Favorities.route) {
            ScreenContent(
                name = BottomBarScreen.Favorities.route
            )
        }
        composable(route = BottomBarScreen.Map.route) {
            ScreenContent(
                name = BottomBarScreen.Map.route,
            )
        }

    }

}

sealed class AuthScreen(val route: String) {
    object Root : AuthScreen(route = "ROOT")
}