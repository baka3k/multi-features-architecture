package com.baka3k.architecture.feature.movie.interactor

import com.baka3k.core.common.interactor.UpStreamSingleUseCase
import com.baka3k.core.common.result.Result
import com.baka3k.core.common.result.asResult
import com.baka3k.core.model.Movie
import com.baka3k.core.model.PagingInfo
import com.baka3k.data.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUpComingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UpStreamSingleUseCase<Flow<Result<List<Movie>>>> {
    private val pageInfo = PagingInfo(page = 0, numberItems = 20)
    override fun invoke(): Flow<Result<List<Movie>>> {
        return movieRepository.getUpCommingMovieStream().asResult()
    }

    suspend fun loadMore() {
        pageInfo.page += 1
        movieRepository.loadMoreUpComingFromServer(pageInfo)
    }
}