package rick_and_morty.ui.episodes.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import rick_and_morty.BottomNavigationBar
import rick_and_morty.eventbus.EventBus
import rick_and_morty.ui.characters.CharactersViewModel
import rick_and_morty.ui.episodes.EpisodesViewModel
import rick_and_morty.ui.widgets.CircularProgressBar
import rick_and_morty.ui.widgets.RickAndMortyErrorDialog

@Composable
fun EpisodeList(
        episodesViewModel: EpisodesViewModel = viewModel(modelClass = EpisodesViewModel::class.java),
) {

        val episodes = episodesViewModel.episodes.collectAsState().value
        val scrollListState = rememberLazyListState()
        if (episodes.isLoading)
        {
                CircularProgressBar()
        }
        when {
                episodes.isFailure -> RickAndMortyErrorDialog(episodes.failure.toString())
                else -> {
                        LazyColumn (
                                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                state = scrollListState
                        ) {
                                items(episodes.episodeResults) { characters ->
                                        EpisodesRow(
                                                episodeResultsDto = characters,
                                          )
                                }
                        }
                        val userIsAtBottom by remember{
                                derivedStateOf {
                                        val layoutInfo = scrollListState.layoutInfo
                                        val visibleItemsInfo = layoutInfo.visibleItemsInfo
                                        if (layoutInfo.totalItemsCount == 0) {
                                                false
                                        } else {
                                                val lastVisibleItem = visibleItemsInfo.last()
                                                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
                                                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                                                        lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
                                        }
                                }
                        }
                        if (userIsAtBottom){
                                LaunchedEffect(true) {
                                        episodesViewModel.getEpisodes()
                                }
                        }
                }
        }
}