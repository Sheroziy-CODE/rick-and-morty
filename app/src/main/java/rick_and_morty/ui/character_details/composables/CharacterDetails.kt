import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import rick_and_morty.ui.character_details.CharacterDetailsViewModel
import rick_and_morty.ui.character_details.composables.CharacterDetailsRow
import rick_and_morty.ui.widgets.RickAndMortyErrorDialog
import rick_and_morty.ui.widgets.CircularProgressBar

@Composable
fun CharacterDetails(characterDetailsViewModel: CharacterDetailsViewModel = viewModel(modelClass = CharacterDetailsViewModel::class.java)) {

    val characters = characterDetailsViewModel.characters.collectAsState().value
    when {
        characters.isLoading -> CircularProgressBar()
        characters.isFailure -> RickAndMortyErrorDialog(characters.failure.toString())
        else -> characters.characterResultDetails?.let { CharacterDetailsRow(characterResultsDto = it) }
    }
}
