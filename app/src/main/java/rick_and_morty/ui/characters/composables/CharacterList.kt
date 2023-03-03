import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import rick_and_morty.ui.characters.CharactersViewModel

@Composable
fun CharacterList(charactersViewModel: CharactersViewModel = viewModel(modelClass = CharactersViewModel::class.java)) {

    val characters = charactersViewModel.characters.collectAsState(emptyList())
    val scrollListState = rememberLazyListState()

    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(5.dp),
        state = scrollListState
    ) {
        items(characters.value) { characters ->
            CharacterRow(characterResultsDto = characters)
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
