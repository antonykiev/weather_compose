package com.test.weatherspbmsc.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test.weatherspbmsc.features.favorities.FavoritesScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.FAVORITES
    ) {
        composable(route = Graph.FAVORITES) {
            FavoritesScreen()
        }
    }
}

object Graph {
    const val ROOT      = "root_graph"
    const val FAVORITES = "favorites_graph"
    const val MAP       = "map_graph"
}