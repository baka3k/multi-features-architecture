package com.baka3k.test.feature.movie.ui.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.baka3k.architecture.core.ui.BaseFragment
import com.baka3k.architecture.core.ui.collectsEvents
import com.baka3k.core.navigation.navigateScreen
import com.baka3k.core.navigation.toUriWithParams
import com.baka3k.test.feature.movie.databinding.MovieFragmentBinding
import com.baka3k.test.feature.movie.router.MovieListScreenRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<MovieFragmentBinding>() {
    private val viewModel: MovieListViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding? {
        return MovieFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private lateinit var movieListAdapter: MovieListAdapter
    private fun initObservers() {
        movieListAdapter = MovieListAdapter(emptyList()) {
            if (it is MovieState.Data) {
                val movieID = "${it.movie.id}"
                val name = "${it.movie.title}"
                val poster = "${it.movie.backdropPath}"
                gotoDetailScreen(movieID, name, poster)
            }
        }
        binding.recycleView.adapter = movieListAdapter
        viewModel.popularUiState.collectsEvents(lifecycleScope, viewLifecycleOwner) {
            Log.d("MoviesFragment", "Query done")
            when (it) {
                PopularUiState.Loading -> {
                    Log.d("MoviesFragment", "Loading")
                    movieListAdapter.showLoadMore()
                }
                PopularUiState.Error -> {
                    Log.d("MoviesFragment", "Error")
                }
                is PopularUiState.Success -> {
                    Log.d("MoviesFragment", "${it.movies.size}")
                    movieListAdapter.fillData(it.movies)
                }
            }
//        viewModel.nowPlayingUiState.collectsEvents(lifecycleScope, viewLifecycleOwner) {
//            Log.d("MoviesFragment", "$it")
//            when (it) {
//                NowPlayingUiState.Loading -> {
//
//                }
//                NowPlayingUiState.Error -> {
//
//                }
//                is NowPlayingUiState.Success -> {
//                    binding.recycleView.adapter = MovieListAdapter(it.movies) {
//
//                    }
//                }
//            }
//
        }
    }

    private fun gotoDetailScreen(movieID: String, name: String, poster: String) {
        val params = mapOf(
            "idmovie" to movieID,
            "name" to name,
            "poster" to poster
        )
        var uri = "android-app://com.baka3k.test.feature.moviedetail.router/moviedetailfragment".toUriWithParams(params)
        findNavController().navigate(uri)
    }
}
