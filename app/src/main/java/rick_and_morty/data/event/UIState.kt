package rick_and_morty.data.event

import rick_and_morty.data.model.CharacterResultsDto

data class UIState (
    val isLoading : Boolean = false,
    val characterResults: List<CharacterResultsDto> = emptyList(),
    val isSuccessDetails: CharacterResultsDto? = null,
    val failure: Exception? = null
)
{
    val isFailure: Boolean = failure != null
}
