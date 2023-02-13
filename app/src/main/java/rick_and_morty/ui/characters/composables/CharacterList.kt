import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rick_and_morty.ui.characters.CharactersViewModel

@Composable
fun CharacterList(charactersViewModel: CharactersViewModel = viewModel(modelClass = CharactersViewModel::class.java)) {

    val characters = charactersViewModel.characters.collectAsState(initial = emptyList())

    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        items(characters.value) {characters ->
            CharacterRow(characterResultsDto = characters)
        }
        item {
            LaunchedEffect(true) {
                    charactersViewModel.getCharacters()
          }
        }
    }
}



/*
DONT CHECK BELOW
----------------------------------------------------------------

val rickAndMortyResult = characterListViewModels.allCharacterLists

val nextPage = characterListViewModels.rickAndMortyResponse.value.info?.next

val listState = rememberLazyListState()
LazyColumn is not updating after ListViewModel is triggered

To locate if user scrolled till the end of the list
    val isAtBottom by remember{
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
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

  if (!listState.canScrollForward && !characterListViewModels.initialLoad) {
      page += 1
       characterListViewModels.getCharacters(page)
       Log.i("HERE", "NO YOU CANT")
   }


           item {
            item{
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
}
*/
