package com.baka3k.data.movie.network.datasource

import com.baka3k.data.movie.network.BuildConfig
import com.baka3k.data.movie.network.model.NetworkMovieResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitMovieNetworkApi {
    @GET(value = "popular")
    suspend fun getPopularMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
//    ): NetworkResponse<NetworkMovieResponse>

    @GET(value = "top_rated")
    suspend fun getTopRateMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse

    @GET(value = "upcoming")
    suspend fun getUpCommingMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
//    ): NetworkResponse<NetworkMovieResponse>


    @GET(value = "now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
    @GET("/3/search/movie")
    suspend fun findMovieFromServer(
        @Query("query") query: String = "",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
}