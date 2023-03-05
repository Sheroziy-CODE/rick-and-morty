package rick_and_morty.data.event

import rick_and_morty.data.model.CharacterResultsDto

data class UIState (
    var isLoading : Boolean = false,
    val isSuccess: List<CharacterResultsDto> = emptyList(),
    val isFailure: Boolean = false,
    val failureMessage: Exception? = null
)
