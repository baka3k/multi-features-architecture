package com.baka3k.architecture.feature.movie.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.baka3k.architecture.feature.movie.MovieRoute
import com.baka3k.core.navigation.Screen

object MovieDestination : Screen {
    override val startScreen: String
        get() = "movie_router"
    override val destinationScreen: String
        get() = "movie_destination"
    override val deepLinkUrl: String
        get() = ""
}
fun NavGraphBuilder.movieScreenComposeGraph(
    windowSizeClass: WindowSizeClass
) {
    composable(route = MovieDestination.startScreen) {
        MovieRoute()
    }
}
