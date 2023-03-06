import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rick_and_morty.ui.characters.CharactersViewModel
import rick_and_morty.ui.widgets.RickAndMortyErrorDialog
import rick_and_morty.ui.widgets.CircularProgressBar

@Composable
fun CharacterList(charactersViewModel: CharactersViewModel = viewModel(modelClass = CharactersViewModel::class.java)) {

    val characters = charactersViewModel.characters.collectAsState().value
    if (characters.isLoading)
    {
        CircularProgressBar()
    }
    when {
        characters.isFailure -> RickAndMortyErrorDialog(characters.failureMessage.toString())
        else -> LazyColumn (verticalArrangement = Arrangement.spacedBy(5.dp))
        {
            items(characters.isSuccess) {characters ->
                CharacterRow(characterResultsDto = characters)
            }
            item {
                LaunchedEffect(true) {
                    charactersViewModel.getCharacters()
                }
            }
        }
    }
}
