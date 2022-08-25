package com.baka3k.test.feature.movie.ui.movielist

import com.baka3k.core.model.Movie

sealed interface PopularUiState {
    data class Success(val movies: List<MovieState>) : PopularUiState
    object Error : PopularUiState
    object Loading : PopularUiState
}

sealed interface NowPlayingUiState {
    data class Success(val movies: List<MovieState>) : NowPlayingUiState
    object Error : NowPlayingUiState
    object Loading : NowPlayingUiState
}

data class MovieScreenUiState(
    val nowPlayingUiState: NowPlayingUiState,
    val popularUiState: PopularUiState
)

sealed interface MovieState {
    data class Data(val movie: Movie) : MovieState
    object Loading : MovieState
}

fun Movie.toMovieState(): MovieState {
    return MovieState.Data(this)
}

fun Movie.toMovieStateLoading(): MovieState {
    return MovieState.Loading
}
