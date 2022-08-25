package com.baka3k.core.database

import com.baka3k.core.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesMovieDao(
        database: HiDatabase,
    ): MovieDao = database.movieDao()
}
