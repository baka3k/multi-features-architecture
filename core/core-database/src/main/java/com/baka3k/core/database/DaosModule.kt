package com.baka3k.core.database

import com.baka3k.core.database.dao.AuthorDao
import com.baka3k.core.database.dao.EpisodeDao
import com.baka3k.core.database.dao.MovieDao
import com.baka3k.core.database.dao.NewsResourceDao
import com.baka3k.core.database.dao.TopicDao
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

    @Provides
    fun providesAuthorDao(
        database: HiDatabase,
    ): AuthorDao = database.authorDao()

    @Provides
    fun providesTopicsDao(
        database: HiDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesEpisodeDao(
        database: HiDatabase,
    ): EpisodeDao = database.episodeDao()

    @Provides
    fun providesNewsResourceDao(
        database: HiDatabase,
    ): NewsResourceDao = database.newsResourceDao()
}
