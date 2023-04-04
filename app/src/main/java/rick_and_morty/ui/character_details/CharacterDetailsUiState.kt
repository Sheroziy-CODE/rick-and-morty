package rick_and_morty.ui.character_details

import rick_and_morty.data.model.CharacterResultsDto

data class CharacterDetailsUiState (
    val isLoading : Boolean = true,
    val characterResultDetails:  CharacterDetails = CharacterDetails("", emptyList()),
    val failure: Exception? = null
)
{
    val isFailure: Boolean = failure != null
}
