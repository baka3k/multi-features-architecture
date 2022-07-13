package com.baka3k.core.data.repository

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovieStream(): Flow<List<Movie>>
    fun getNowPlayingMovieStream(): Flow<List<Movie>>
    fun getTopRateMovieStream(): Flow<List<Movie>>
    fun getUpCommingMovieStream(): Flow<List<Movie>>

    // just for test
    fun getMovieStream(): Flow<List<Movie>>

    suspend fun loadMorePopular(page: Int): Result<List<Movie>>
    suspend fun loadMoreNowPlaying(page: Int): Result<List<Movie>>
}