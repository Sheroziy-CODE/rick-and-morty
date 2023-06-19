import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rick_and_morty.ui.characters.CharactersViewModel
import rick_and_morty.ui.widgets.RickAndMortyErrorDialog
import rick_and_morty.ui.widgets.CircularProgressBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterList(
    charactersViewModel: CharactersViewModel = viewModel(modelClass = CharactersViewModel::class.java),
) {

    val characters = charactersViewModel.characters.collectAsState().value
    val scrollListState = rememberLazyListState()

    val swipeRefreshState = rememberPullRefreshState(characters.isLoading, {charactersViewModel.refreshCharacters()})

    Box(modifier = Modifier.fillMaxSize().pullRefresh(swipeRefreshState)) {
        if (characters.isLoading && characters.characterResults.isEmpty()) {
            CircularProgressBar()
        }
        when {
            characters.isFailure -> RickAndMortyErrorDialog(characters.failure.toString())
            else -> {
                LazyColumn(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    state = scrollListState
                ) {
                    items(characters.characterResults) { character ->
                        CharacterRow(
                            characterResultsDto = character,
                            onNavigateToCharacterDetails = {
                                charactersViewModel.onCharacterSelected(
                                    character.id
                                )
                            }
                        )
                    }
                }
                val userIsAtBottom by remember {
                    derivedStateOf {
                        val layoutInfo = scrollListState.layoutInfo
                        val visibleItemsInfo = layoutInfo.visibleItemsInfo
                        if (layoutInfo.totalItemsCount == 0) {
                            false
                        } else {
                            val lastVisibleItem = visibleItemsInfo.last()
                            val viewportHeight =
                                layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
                            (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                                    lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
                        }
                    }
                }
                if (userIsAtBottom) {
                    LaunchedEffect(true) {
                        charactersViewModel.getCharacters()
                    }
                }
            }
        }
        PullRefreshIndicator(characters.isLoading, swipeRefreshState, Modifier.align(Alignment.TopCenter))
    }
}