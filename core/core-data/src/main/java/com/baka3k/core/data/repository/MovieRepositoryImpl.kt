package com.baka3k.core.data.repository

import android.util.Log
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.model.asEntity
import com.baka3k.core.data.model.asNowPlayingEntity
import com.baka3k.core.data.model.asNowPlayingMovie
import com.baka3k.core.data.model.asPopularEntity
import com.baka3k.core.data.model.asPopularMovie
import com.baka3k.core.data.model.asUpStream
import com.baka3k.core.database.dao.MovieDao
import com.baka3k.core.database.model.asExternalModel
import com.baka3k.core.datastore.HiPreferencesDataSource
import com.baka3k.core.model.Movie
import com.baka3k.core.network.datasource.MovieNetworkDataSource
import com.baka3k.core.network.model.NetworkMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val network: MovieNetworkDataSource,
    private val preference: HiPreferencesDataSource
) : MovieRepository {
    override fun getNowPlayingMovieStream(): Flow<List<Movie>> =
        movieDao.getNowPlayingEntitiesStream().map { data ->
            data.map { it.asExternalModel() }
        }

    override fun getPopularMovieStream(): Flow<List<Movie>> =
        movieDao.getPolularMovieEntitiesStream().map { data ->
            data.map { it.asExternalModel() }
        }

    override fun getTopRateMovieStream(): Flow<List<Movie>> =
        movieDao.getTopRateMovieEntitiesStream().map { data ->
            data.map { it.asExternalModel() }
        }

    override fun getUpCommingMovieStream(): Flow<List<Movie>> =
        movieDao.getUpCommingMovieEntitiesStream().map { data ->
            data.map { it.asExternalModel() }
        }

    override fun getMovieStream(): Flow<List<Movie>> {
        Log.d(TAG, "#getMovieStream()")
        val dataLocal = movieDao.getMovieEntitiesStream().map { data ->
            data.map { it.asExternalModel() }
        }
        return dataLocal
    }

    override suspend fun loadMorePopular(page: Int): Result<List<Movie>> {
        return when (val response = network.getPopularMovie(page)) {
            is Result.Success -> {
                val data = response.data
                movieDao.upsertMovie(data.map(NetworkMovie::asPopularEntity))
                Result.Success(data.map(NetworkMovie::asPopularMovie))
            }
            is Result.Error -> {
                Result.Error(response.exception)
            }
            else -> {
                Result.Success(emptyList())
            }
        }
    }

    override suspend fun loadMoreNowPlaying(page: Int): Result<List<Movie>> {
        return when (val response = network.getNowPlayingMovie(page)) {
            is Result.Success -> {
                val data = response.data
                movieDao.upsertMovie(data.map(NetworkMovie::asNowPlayingEntity))
                Result.Success(data.map(NetworkMovie::asNowPlayingMovie))
            }
            is Result.Error -> {
                Result.Error(response.exception)
            }
            else -> {
                Result.Success(emptyList())
            }
        }
    }

    companion object {
        private const val TAG = "MovieRepositoryImpl"
    }
}