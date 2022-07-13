package com.baka3k.core.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.core.network.model.NetworkMovie

interface MovieNetworkDataSource {
    suspend fun getPopularMovie(page: Int = 1): Result<List<NetworkMovie>>
    suspend fun getTopRateMovie(page: Int = 1): Result<List<NetworkMovie>>
    suspend fun getUpCommingMovie(page: Int = 1): Result<List<NetworkMovie>>
    suspend fun getNowPlayingMovie(page: Int = 1): Result<List<NetworkMovie>>
}