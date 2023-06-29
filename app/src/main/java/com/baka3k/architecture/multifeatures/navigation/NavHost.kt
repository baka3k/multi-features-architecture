package com.baka3k.architecture.multifeatures.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.baka3k.architecture.feature.movie.list.navigation.SearchMovieDestination
import com.baka3k.architecture.feature.movie.list.navigation.searchMoviesComposeGraph
import com.baka3k.architecture.feature.movie.navigation.MovieDestination
import com.baka3k.architecture.feature.movie.navigation.movieScreenComposeGraph
import com.baka3k.core.navigation.Screen
import com.baka3k.test.movie.detail.navigation.MovieDetailDestination
import com.baka3k.test.movie.detail.navigation.movieDetailComposeGraph
import com.baka3k.test.movie.person.navigation.PersonScreenDestination
import com.baka3k.test.movie.person.navigation.personScreenComposeGraph

@Composable
fun HiNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToScreen: (Screen, String) -> Unit,
    startScreen: String = MovieDestination.startScreen,
    onBackPress: () -> Unit
) {
    NavHost(navController = navController, startDestination = startScreen, modifier = modifier)
    {
        movieScreenComposeGraph(
            navigateToMovieDetail = {
                onNavigateToScreen(
                    MovieDetailDestination,
                    MovieDetailDestination.createNavigationRoute(it.toString())
                )
            },
            navigateToSearchMovie = {
                onNavigateToScreen(
                    SearchMovieDestination,
                    SearchMovieDestination.createNavigationRoute(it)
                )
            }
        )
        movieDetailComposeGraph(onBackPress = onBackPress, navigateToPersonScreen = { personId ->
            onNavigateToScreen(
                PersonScreenDestination,
                PersonScreenDestination.createNavigationRoute(personId.toString())
            )
        })
        personScreenComposeGraph(onBackPress = onBackPress)
        searchMoviesComposeGraph(onBackPress = onBackPress, navigateToMovieDetail = {
            onNavigateToScreen(
                MovieDetailDestination,
                MovieDetailDestination.createNavigationRoute(it.toString())
            )
        })
    }
}


