package com.baka3k.core.data.di

import com.baka3k.core.data.repository.MovieRepository
import com.baka3k.core.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsMovieRepository(
        movieRepository: MovieRepositoryImpl
    ): MovieRepository
}
