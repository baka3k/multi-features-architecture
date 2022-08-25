package com.baka3k.architecture.feature.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.baka3k.architecture.core.ui.compose.ShimmerList
import com.baka3k.core.common.logger.Logger
import java.time.format.DateTimeFormatter


@Composable
fun MovieRoute(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val uiNowPlayingUiState by viewModel.nowPlayingUiState.collectAsState()
    val uiPopularUiState by viewModel.popularUiState.collectAsState()
    val popularLoadMoreState = rememberLazyListState()
    MoviesScreen(popularLoadMoreState, uiNowPlayingUiState, uiPopularUiState)
    popularLoadMoreState.OnBottomReached {
        viewModel.loadMorePopular()
    }
}

@Composable
fun MoviesScreen(
    listState: LazyListState,
    nowPlayingUiState: NowPlayingUiState,
    popularUiState: PopularUiState
) {
    Column(horizontalAlignment = CenterHorizontally) {
        nowPlayingView(nowPlayingUiState)
        popularView(listState,popularUiState)
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}

@Composable
private fun nowPlayingView(nowPlayingUiState: NowPlayingUiState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(216.dp)
    ) {
        when (nowPlayingUiState) {
            NowPlayingUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
            NowPlayingUiState.Error -> {
                Text(text = "$nowPlayingUiState")
            }
            is NowPlayingUiState.Success -> {
                LazyRow {

                    items(nowPlayingUiState.movies) {
                        AsyncImage(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .size(216.dp),
                            contentScale = ContentScale.Crop,
                            model = ImageRequest.Builder(LocalContext.current).data(it.backdropPath)
                                .diskCachePolicy(CachePolicy.ENABLED).build(),
                            contentDescription = it.title,
                        )
                    }
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun popularView(listState:LazyListState,popularUiState: PopularUiState) {
    Box(modifier = Modifier.fillMaxSize())
    {
        when (popularUiState) {
            PopularUiState.Loading -> {
                ShimmerList()
            }
            PopularUiState.Error -> {
                Text(text = "$popularUiState")
            }
            is PopularUiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    items(popularUiState.movies) {
                        Card(
                            shape = RoundedCornerShape(1.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Column {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Row {
                                            Text(
                                                it.title,
                                                style = MaterialTheme.typography.headlineSmall,
                                                modifier = Modifier.fillMaxWidth((.8f))
                                            )

                                        }
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            dateFormatted(it.releaseDate),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            it.overview,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                                AsyncImage(
                                    placeholder = painterResource(com.baka3k.architecture.core.ui.R.drawable.ic_placeholder_default),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    contentScale = ContentScale.Crop,
                                    model = it.backdropPath,
                                    contentDescription = null // decorative image
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
private fun dateFormatted(publishDate: String): String {
    val dateParserFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = dateParserFormat.parse(publishDate)
    val dateOutputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return dateOutputFormat.format(date)
}