package com.baka3k.core.network.di

import com.baka3k.core.network.datasource.MovieNetworkDataSource
import com.baka3k.core.network.datasource.retrofit.movie.RetrofitMovieNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindsNiaNetwork(
//        niANetwork: FakeNiaNetworkDataSource
        niANetwork: RetrofitMovieNetworkDataSource
    ): MovieNetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}