package com.baka3k.data.movie.network.di

import com.baka3k.data.movie.network.datasource.MovieNetworkDataSource
import com.baka3k.data.movie.network.datasource.RetrofitMovieNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindsNiaNetwork(
        niANetwork: RetrofitMovieNetworkDataSource
    ): MovieNetworkDataSource

//    companion object {
//        @Provides
//        @Singleton
//        fun providesNetworkJson(): Json = Json {
//            ignoreUnknownKeys = true
//            coerceInputValues = true
//        }
//    }
}
