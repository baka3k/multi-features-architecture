package com.baka3k.architecture.feature.movie.list

import com.baka3k.core.model.Movie

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
}