package rick_and_morty.data.event

import rick_and_morty.data.model.CharacterResultsDto

sealed class ResultEvent<T> {
    data class Success(var list: List<CharacterResultsDto>): ResultEvent<List<CharacterResultsDto>>()
    data class Error(val exception: Exception) : ResultEvent<List<CharacterResultsDto>>()
}
