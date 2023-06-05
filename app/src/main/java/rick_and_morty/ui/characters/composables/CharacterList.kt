import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.ui.characters.CharactersViewModel
import rick_and_morty.ui.widgets.RickAndMortyErrorDialog
import rick_and_morty.ui.widgets.CircularProgressBar

@Composable
fun CharacterList(
    charactersViewModel: CharactersViewModel = viewModel(modelClass = CharactersViewModel::class.java),
) {

    val characters = charactersViewModel.characters.collectAsState().value
    val scrollListState = rememberLazyListState()
    if (characters.isLoading)
    {
        CircularProgressBar()
    }
    when {
        characters.isFailure -> RickAndMortyErrorDialog(characters.failure.toString())
        else -> {
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(5.dp),
                state = scrollListState
            ) {
                items(characters.characterResults) { characters ->
                    CharacterRow(
                        characterResultsDto = characters,
                        onNavigateToCharacterDetails = { charactersViewModel.onCharacterSelected(characters.id) }
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
                    charactersViewModel.getCharacters()
                }
            }
        }
    }
}