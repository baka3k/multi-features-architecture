package com.baka3k.architecture.feature.movie.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baka3k.architecture.feature.movie.list.interactor.GetMovieUseCase
import com.baka3k.architecture.feature.movie.list.navigation.SearchMovieDestination
import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {
    private val _query: String = checkNotNull(
        savedStateHandle[SearchMovieDestination.queryArg]
    )
    private val dataStream: MutableStateFlow<Result<List<Movie>>> = MutableStateFlow(Result.Loading)
    val moviesState = dataStream.map {
        val data = when (it) {
            is Result.Success -> MovieListUiState.Success(it.data)
            is Result.Loading -> MovieListUiState.Loading
            is Result.Error -> MovieListUiState.Error
            else -> {
                MovieListUiState.Success(emptyList())
            }
        }
        data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieListUiState.Loading
    )

    init {
        searchMovie(_query)
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            dataStream.value = Result.Loading
            val result = getMovieUseCase.invoke(query)
            dataStream.value = result
        }
    }
}