package com.baka3k.data.movie.network.di

import android.content.Context
import com.baka3k.data.movie.network.datasource.SSlContextVerification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SSLModule {
    @Provides
    @Singleton
    fun sslVerification(
        @ApplicationContext context: Context,
    ): SSlContextVerification = SSlContextVerification(context.assets)
}
