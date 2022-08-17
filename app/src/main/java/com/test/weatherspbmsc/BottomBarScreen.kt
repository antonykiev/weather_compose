package com.test.weatherspbmsc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Favorities: BottomBarScreen(
        route = "FAVORITIES",
        title = "FAVORITIES",
        icon = Icons.Default.Favorite
    )

    object Map: BottomBarScreen(
        route = "MAP",
        title = "MAP",
        icon = Icons.Default.Map
    )

}