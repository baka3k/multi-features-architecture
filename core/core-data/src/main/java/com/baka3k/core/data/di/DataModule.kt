package com.baka3k.core.data.di

import com.baka3k.core.data.movie.repository.CreditRepository
import com.baka3k.core.data.movie.repository.GenreRepository
import com.baka3k.core.data.movie.repository.PersonRepository
import com.baka3k.core.data.movie.repository.ReviewRepository
import com.baka3k.core.data.movie.repository.real.CreditRepositoryImpl
import com.baka3k.core.data.movie.repository.real.GenreRepositoryImpl
import com.baka3k.core.data.movie.repository.real.PersonRepositoryImpl
import com.baka3k.core.data.movie.repository.real.ReviewRepositoryImpl

import com.baka3k.data.movie.MovieRepository
import com.baka3k.data.movie.MovieRepositoryImpl
import com.baka3k.data.movie.MovieTypeRepository
import com.baka3k.data.movie.MovieTypeRepositoryImpl
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

    @Binds
    fun bindsMovieTypeRepository(
        movieTypeRepository: MovieTypeRepositoryImpl
    ): MovieTypeRepository

    @Binds
    fun bindsCreditRepository(
        creditRepository: CreditRepositoryImpl
    ): CreditRepository

    @Binds
    fun bindsGenreRepository(
        genreRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    fun bindsPersonRepository(
        personRepository: PersonRepositoryImpl
    ): PersonRepository

    @Binds
    fun bindsReviewRepository(
        reviewRepository: ReviewRepositoryImpl
    ): ReviewRepository
}
