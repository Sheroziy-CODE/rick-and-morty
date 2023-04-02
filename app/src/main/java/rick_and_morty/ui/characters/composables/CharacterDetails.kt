import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.ui.characters.CharacterDetailsViewModel
import rick_and_morty.ui.widgets.RickAndMortyErrorDialog
import rick_and_morty.ui.widgets.CircularProgressBar

@Composable
fun CharacterDetails(characterDetailsViewModel: CharacterDetailsViewModel = viewModel(modelClass = CharacterDetailsViewModel::class.java)) {

    val characters = characterDetailsViewModel.characters.collectAsState().value
    if (characters.isLoading)
    {
        CircularProgressBar()
    }
    when {
        characters.isFailure -> RickAndMortyErrorDialog(characters.failureMessage.toString())
        else -> characters.isSuccessDetails?.let { CharacterDetailsRow(characterResultsDto = it) }
    }
}
