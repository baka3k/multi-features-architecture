package com.baka3k.architecture.feature.movie.di

import com.baka3k.architecture.feature.movie.interactor.GetMoviePopularUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
//    @Binds
//    fun bindUseCaseGetPolular(useCase: GetMoviePopularUseCase):GetMoviePopularUseCase
}