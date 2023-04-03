package rick_and_morty.ui.characters

import rick_and_morty.data.model.CharacterResultsDto

data class CharactersUiState (
    val isLoading : Boolean = true,
    val characterResults: List<CharacterResultsDto> = emptyList(),
    val failure: Exception? = null
)
{
    val isFailure: Boolean = failure != null
}
