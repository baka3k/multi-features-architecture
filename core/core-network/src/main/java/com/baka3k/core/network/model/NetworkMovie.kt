package com.baka3k.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovie(
    val adult: Boolean =false,
    val backdropPath: String = "",
    val genreIDS: List<Long> = emptyList(),
    val id: Long = 0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Long = 0
)
@Serializable
data class NetworkMovieResponse(
    val page: Long = 0 ,
    val results: List<NetworkMovie> = emptyList(),
    val totalPages: Long = 0,
    val totalResults: Long = 0
)
