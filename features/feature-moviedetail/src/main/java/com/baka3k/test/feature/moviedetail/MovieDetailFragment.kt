package com.baka3k.test.feature.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import coil.load
import com.baka3k.architecture.core.ui.BaseFragment
import com.baka3k.architecture.core.ui.collectsEvents
import com.baka3k.test.feature.moviedetail.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var imageView: ImageView
    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding? {
        return FragmentMovieDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        imageView = view.findViewById(R.id.imageView)

    }

    private fun initObservers() {
        viewModel.movieDetailUIState.collectsEvents(lifecycleScope, viewLifecycleOwner) {
            Log.d("MovieDetailFragment", "Query done")
            when (it) {
                MovieDetailUiState.Loading -> {
                    Log.d("MovieDetailFragment", "Loading")
                }
                MovieDetailUiState.Error -> {
                    Log.d("MovieDetailFragment", "Error")
                }
                is MovieDetailUiState.Success -> {
                    Log.d("MovieDetailFragment", "${it.movie}")
                    imageView.load(it.movie.backdropPath) {
                        crossfade(true)
                        placeholder(androidx.appcompat.R.drawable.abc_dialog_material_background)
                    }
                }
            }
        }
    }
}