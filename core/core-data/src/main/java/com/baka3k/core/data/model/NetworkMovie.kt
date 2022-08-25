package com.baka3k.core.data.model

import com.baka3k.core.database.dao.MovieDao
import com.baka3k.core.database.model.MovieEntity
import com.baka3k.core.model.Movie
import com.baka3k.core.network.model.NetworkMovie


fun NetworkMovie.asEntity(type: Int = 1) = MovieEntity(
    id = id,
    adult = adult,
    backdropPath = "https://image.tmdb.org/t/p/w300${backdrop_path}",
    genreIDS = "emptyList()",
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = "https://image.tmdb.org/t/p/w300${poster_path}",
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    type = type
)
fun NetworkMovie.asUpStream(type: Int = 1) = Movie(
    id = id,
    adult = adult,
    backdropPath = backdrop_path,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = poster_path,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun NetworkMovie.asPopularEntity() = asEntity(type = MovieDao.POPULAR)
fun NetworkMovie.asNowPlayingEntity() = asEntity(type = MovieDao.NOW_PLAYING)

fun NetworkMovie.asPopularMovie() = asUpStream(type = MovieDao.POPULAR)
fun NetworkMovie.asNowPlayingMovie() = asUpStream(type = MovieDao.NOW_PLAYING)