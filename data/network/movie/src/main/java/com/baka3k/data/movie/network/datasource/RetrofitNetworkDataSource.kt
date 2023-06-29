package com.baka3k.data.movie.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.data.movie.network.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


open abstract class RetrofitNetworkDataSource<T : Any>(
    private val networkJson: Json,
    private val sslContextVerification: SSlContextVerification,
    service: Class<T>,
) {
    companion object {
        private const val backend_movie_url = "${BuildConfig.BACKEND_MOVIE_URL}/3/movie/"
    }
    protected val networkApi = Retrofit.Builder().baseUrl(backend_movie_url).client(netWorkClient())
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build().create(service)
    private fun netWorkClient(): OkHttpClient {
        return OkHttpClient.Builder().certificatePinner(CertificatePinner.Builder().apply {
            for ((key, value) in sslContextVerification.pinnings) {
                add(value, key)
            }
        }.build())
            .sslSocketFactory(
                sslContextVerification.sslSocketFactory,
                sslContextVerification.trustManager
            )
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .build()
    }

    protected inline fun <R> invokeCatching(block: () -> R): Result<R> {
        return com.baka3k.core.common.result.runCatching {
            block.invoke()
        }
    }
}