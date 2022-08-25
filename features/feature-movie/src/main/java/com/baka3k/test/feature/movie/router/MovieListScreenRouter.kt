package com.baka3k.test.feature.movie.router

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.baka3k.core.navigation.Screen
import com.baka3k.test.feature.movie.ui.movielist.MoviesFragment

object MovieListScreenRouter : Screen {
    override val startScreen: String
        get() = "movie_router"
    override val destinationScreen: String
        get() = "movie_destination"
    override val deepLinkUrl: String
        get() = "android-app://com.baka3k.test.feature.movie.router/MoviesFragment"
}

inline fun NavController.graphScreenMovieList(
    navigateMovieDetail: (String) -> Unit,
    navigateToActor: (String) -> Unit,
): NavGraph {
    return createGraph(
        startDestination = MovieListScreenRouter.startScreen,
    ) {
        fragment<MoviesFragment>(MovieListScreenRouter.startScreen) {
            label = "movie"
        }
    }
}