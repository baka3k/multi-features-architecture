package com.baka3k.architecture.feature.movie.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baka3k.architecture.core.ui.component.AsyncImageView
import com.baka3k.architecture.core.ui.component.SearchEditText
import com.baka3k.architecture.core.ui.component.ShimmerList
import com.baka3k.architecture.core.ui.theme.AppTheme
import com.baka3k.architecture.feature.movie.list.MovieListUiState.Loading
import com.baka3k.architecture.feature.movie.list.MovieListUiState.Success
import com.baka3k.core.common.logger.Logger
import com.baka3k.core.data.movie.model.PhotoSize
import com.baka3k.core.data.movie.model.asPhotoUrl
import com.baka3k.core.model.Movie


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchMoviesRoute(
    navigateToMovieDetail: (Long) -> Unit,
    viewModel: SearchMoviesViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val movieListState by viewModel.moviesState.collectAsStateWithLifecycle()
    SearchMoviesScreen(
        navigateToMovieDetail = navigateToMovieDetail,
        movieListUiState = movieListState,
        searchMovie = { query ->
            if (query.isNotEmpty()) {
                viewModel.searchMovie(query)
            } else {
                Logger.d("Empty input")
            }
        }
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchMoviesScreen(
    movieListUiState: MovieListUiState,
    navigateToMovieDetail: (Long) -> Unit,
    searchMovie: (String) -> Unit,
) {
    val reusableModifier = Modifier.fillMaxWidth()
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = reusableModifier) {
        SearchEditText(
            modifier = reusableModifier,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    searchMovie(text)
                }
            )
        ) { value -> text = value }
        movieList(
            modifier = reusableModifier,
            movieListUiState = movieListUiState,
            navigateToMovieDetail = navigateToMovieDetail
        )
    }
}

@Composable
fun movieList(
    modifier: Modifier = Modifier,
    movieListUiState: MovieListUiState,
    navigateToMovieDetail: (Long) -> Unit,
) {
    when (movieListUiState) {
        is Loading -> {
            ShimmerList()
        }

        is Error -> {
            Text(
                "ERR",
                style = MaterialTheme.typography.headlineSmall.copy(color = AppTheme.colors.colorTextHeaderHome),
                modifier = Modifier.fillMaxWidth()
            )
        }

        is Success -> {
            moviesUi(modifier, movieListUiState, navigateToMovieDetail = navigateToMovieDetail)
        }

        else -> {
            Text(
                "UNKNOW ERR",
                style = MaterialTheme.typography.headlineSmall.copy(color = AppTheme.colors.colorTextHeaderHome),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun moviesUi(
    modifier: Modifier,
    movieListUiState: Success,
    navigateToMovieDetail: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 10.dp)
            .background(color = AppTheme.colors.colorBackgroundTheme)
    ) {
        items(items = movieListUiState.movies) { movie ->
            movieItem(movie, navigateToMovieDetail = navigateToMovieDetail)
        }
    }
}

@Composable
private fun movieItem(movie: Movie, navigateToMovieDetail: (Long) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.colorBackgroundTheme),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                navigateToMovieDetail(movie.id)
            }
    ) {
        AsyncImageView(
            data = movie.backdropPath.asPhotoUrl(PhotoSize.Poster.w300),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleMedium.copy(color = AppTheme.colors.colorTextHeaderHome),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = movie.overview,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(203, 206, 212), shadow = Shadow(
                    color = Color(36, 42, 56), offset = Offset(3.0f, 3.0f), blurRadius = 3f
                )
            )
        )
    }
}
