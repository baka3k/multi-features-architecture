package com.baka3k.architecture.feature.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baka3k.core.data.repository.MovieRepository
import com.baka3k.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Empty)
    val uiState: StateFlow<MovieUiState> = combine(
        movieRepository.getNowPlayingMovieStream(),
        movieRepository.getPopularMovieStream(),
    )
    { nowPlayingMovies, popularMovies ->
        MovieUiState.Movies(
            nowPlayingMovies = nowPlayingMovies,
            popularMovies = popularMovies
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieUiState.Loading
    )
    fun test(){

    }

}
//val tabState: StateFlow<InterestsTabState> = _tabState.asStateFlow()
//fun switchTab(newIndex: Int) {
//    if (newIndex != tabState.value.currentIndex) {
//        _tabState.update {
//            it.copy(currentIndex = newIndex)
//        }
//    }
//}
//private val _tabState = MutableStateFlow(
//    InterestsTabState(
//        titles = listOf(R.string.interests_topics, R.string.interests_people),
//        currentIndex = 0
//    )
//)
data class InterestsTabState(
    val titles: List<Int>,
    val currentIndex: Int
)
sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Movies(
        val popularMovies: List<Movie>,
        val nowPlayingMovies: List<Movie>
    ) : MovieUiState()

    object Empty : MovieUiState()
}