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

    val characters = charactersViewModel.characters.collectAsState(emptyList())
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
