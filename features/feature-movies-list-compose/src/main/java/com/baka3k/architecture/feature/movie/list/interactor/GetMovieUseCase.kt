package com.baka3k.architecture.feature.movie.list.interactor

import com.baka3k.core.common.interactor.SingleUseCaseWithParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Movie
import com.baka3k.core.model.PagingInfo
import com.baka3k.data.movie.MovieRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : SingleUseCaseWithParameter<String, Result<List<Movie>>> {
    private val pageInfo = PagingInfo(page = 1, numberItems = 20)
    override suspend fun invoke(query: String): Result<List<Movie>> =
        movieRepository.findMovieFromServer(query = query, pagingConfig = pageInfo)
}