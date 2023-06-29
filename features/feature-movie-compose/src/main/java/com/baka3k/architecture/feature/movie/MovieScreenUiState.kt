package com.baka3k.architecture.feature.movie

import com.baka3k.core.model.Movie

sealed interface PopularUiState {
    data class Success(val movies: List<Movie>) : PopularUiState
    object Error : PopularUiState
    object Loading : PopularUiState
}

sealed interface NowPlayingUiState {
    data class Success(val movies: List<Movie>) : NowPlayingUiState
    object Error : NowPlayingUiState
    object Loading : NowPlayingUiState
}

sealed interface UpComingUiState {
    data class Success(val movies: List<Movie>) : UpComingUiState
    object Error : UpComingUiState
    object Loading : UpComingUiState
}
