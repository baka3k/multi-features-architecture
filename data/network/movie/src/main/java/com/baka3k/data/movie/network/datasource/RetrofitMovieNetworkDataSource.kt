package com.baka3k.data.movie.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.PagingInfo
import com.baka3k.data.movie.network.model.NetworkMovie

import kotlinx.serialization.json.Json
import javax.inject.Inject

class RetrofitMovieNetworkDataSource @Inject constructor(
    networkJson: Json,
    sslContextVerification: SSlContextVerification,
) : RetrofitNetworkDataSource<RetrofitMovieNetworkApi>(
    networkJson, sslContextVerification,RetrofitMovieNetworkApi::class.java
), MovieNetworkDataSource {

    override suspend fun getPopularMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getPopularMovie(page = pagingInfo.page).results
        }
    }
    override suspend fun getTopRateMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getTopRateMovie(page = pagingInfo.page).results
        }
    }

    override suspend fun getUpCommingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getUpCommingMovie(page = pagingInfo.page).results
        }
    }

    override suspend fun getNowPlayingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getNowPlayingMovie(page = pagingInfo.page).results
        }
    }
    override suspend fun findMovie(
        query: String,
        pagingInfo: PagingInfo
    ): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.findMovieFromServer(page = pagingInfo.page, query = query).results
        }
    }

}