package rick_and_morty.data.event

import rick_and_morty.data.model.CharacterResultsDto

sealed class BusEvent<T> {
    data class Success(var list: List<CharacterResultsDto>): BusEvent<List<CharacterResultsDto>>()
    data class Error(val exception: Exception) : BusEvent<List<CharacterResultsDto>>()
}
