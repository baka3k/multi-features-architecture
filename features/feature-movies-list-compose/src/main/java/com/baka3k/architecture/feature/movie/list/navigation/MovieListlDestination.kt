package com.baka3k.architecture.feature.movie.list.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.baka3k.architecture.feature.movie.list.SearchMoviesRoute
import com.baka3k.core.navigation.Screen

object SearchMovieDestination : Screen {
    const val queryArg = "query"
    const val name = "search_movie_destination"
    override val startScreen: String
        get() = "$name/{$queryArg}"
    override val destinationScreen: String
        get() = "${name}_nextscreen"
    override val deepLinkUrl: String
        get() = "android-app://com.baka3k.architecture.feature.movie.list.router/$name"

    fun createNavigationRoute(query: String): String {
        val value = Uri.encode(query)
        return "$name/$value"
    }

    fun createNavigationRouteByDeepLink(query: String): Uri {
        return "$deepLinkUrl?$queryArg=$query".toUri()
    }
}

fun NavGraphBuilder.searchMoviesComposeGraph(
    navigateToMovieDetail: (Long) -> Unit,
    onBackPress: () -> Unit
) {
    composable(
        route = SearchMovieDestination.startScreen,
        arguments = listOf(navArgument(SearchMovieDestination.queryArg) {
            type = NavType.StringType
        }),
        deepLinks = listOf(navDeepLink {
            uriPattern =
                "${SearchMovieDestination.deepLinkUrl}?${SearchMovieDestination.queryArg}={${SearchMovieDestination.queryArg}}"
        })
    ) {
        SearchMoviesRoute(
            navigateToMovieDetail = navigateToMovieDetail,
            onBackPress = onBackPress
        )
    }
}