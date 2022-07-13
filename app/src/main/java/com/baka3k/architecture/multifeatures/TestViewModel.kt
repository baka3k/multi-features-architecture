package com.baka3k.architecture.multifeatures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baka3k.core.data.combine
import com.baka3k.core.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.baka3k.core.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class TestViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Empty)


    val uiState: StateFlow<MovieUiState> = combine(
        movieRepository.getNowPlayingMovieStream(),
        movieRepository.getPopularMovieStream(),
    )
    { nowPlayingMovies, popularMovies ->
//        Log.d("haha", "ViewModel ${nowPlayingMovies.size} / ${popularMovies.size}")
        MovieUiState.Movies(
            nowPlayingMovies = nowPlayingMovies,
            popularMovies = popularMovies
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieUiState.Loading
    )

    var page = 0
    fun test1() {
        viewModelScope.launch(Dispatchers.IO) {
            page += 1
            _uiState.value = MovieUiState.Loading
//            movieRepository.getNowPlayingMovieStream().collect {
//                Log.d("haha", "TestViewModel getNowPlayingMovieStream ${it.size}")
//                _uiState.value =
//                    MovieUiState.Movies(popularMovies = emptyList(), nowPlayingMovies = it)
//            }
//            Log.d("haha", "222222")
//            movieRepository.getPopularMovieStream().collect {
//                Log.d("haha", "TestViewModel getPopularMovieStream ${it.size}")
//                _uiState.value =
//                    MovieUiState.Movies(nowPlayingMovies = emptyList(), popularMovies = it)
//            }
            movieRepository.loadMoreNowPlaying(page = page)
            movieRepository.loadMorePopular(page = page)
//            combine(
//                movieRepository.getTopRateMovieStream(),
//                movieRepository.getPopularMovieStream()
//            ) { nowPlayings, populars ->
//                Log.d("haha", "combine nowPlayings ${nowPlayings.size} populars${populars.size}")
//                _uiState.value =
//                    MovieUiState.Movies(popularMovies = populars, nowPlayingMovies = nowPlayings)
//            }.collect{
//                Log.d("haha", "combine collect?????")
//            }
        }
    }
}

sealed interface MovieUiState {
    object Loading : MovieUiState
    data class Movies(
        val popularMovies: List<Movie>,
        val nowPlayingMovies: List<Movie>
    ) : MovieUiState

    object Empty : MovieUiState
}
//package com.baka3k.architecture.multifeatures
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.baka3k.core.data.combine
//import com.baka3k.core.data.repository.MovieRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import com.baka3k.core.model.Movie
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.flow.update
//import javax.inject.Inject
//
//
//@HiltViewModel
//class TestViewModel @Inject constructor(
//    private val movieRepository: MovieRepository,
//) : ViewModel() {
//    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Empty)
//    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()
////    val uiState1: StateFlow<MovieUiState> = combine(
////        movieRepository.getNowPlayingMovieStream(),
////        movieRepository.getPopularMovieStream(),
////    )
////    { nowPlayingMovies, popularMovies ->
//////        Log.d("haha", "ViewModel ${nowPlayingMovies.size} / ${popularMovies.size}")
////        MovieUiState.Movies(
////            nowPlayingMovies = nowPlayingMovies,
////            popularMovies = popularMovies
////        )
////    }.stateIn(
////        scope = viewModelScope,
////        started = SharingStarted.WhileSubscribed(5_000),
////        initialValue = MovieUiState.Loading
////    )
//
//    var page = 0
//    fun test1() {
//        viewModelScope.launch(Dispatchers.IO) {
//            page += 1
//            _uiState.value = MovieUiState.Loading
////            movieRepository.getNowPlayingMovieStream().collect {
////                Log.d("haha", "TestViewModel getNowPlayingMovieStream ${it.size}")
////                _uiState.value =
////                    MovieUiState.Movies(popularMovies = emptyList(), nowPlayingMovies = it)
////            }
////            Log.d("haha", "222222")
////            movieRepository.getPopularMovieStream().collect {
////                Log.d("haha", "TestViewModel getPopularMovieStream ${it.size}")
////                _uiState.value =
////                    MovieUiState.Movies(nowPlayingMovies = emptyList(), popularMovies = it)
////            }
//            movieRepository.loadMoreNowPlaying(page = page)
//            movieRepository.loadMorePopular(page = page)
////            combine(
////                movieRepository.getTopRateMovieStream(),
////                movieRepository.getPopularMovieStream()
////            ) { nowPlayings, populars ->
////                Log.d("haha", "combine nowPlayings ${nowPlayings.size} populars${populars.size}")
////                _uiState.value =
////                    MovieUiState.Movies(popularMovies = populars, nowPlayingMovies = nowPlayings)
////            }.collect{
////                Log.d("haha", "combine collect?????")
////            }
//        }
//    }
//}
//
//sealed interface MovieUiState {
//    object Loading : MovieUiState
//    data class Movies(
//        val popularMovies: List<Movie>,
//        val nowPlayingMovies: List<Movie>
//    ) : MovieUiState
//
//    object Empty : MovieUiState
//}