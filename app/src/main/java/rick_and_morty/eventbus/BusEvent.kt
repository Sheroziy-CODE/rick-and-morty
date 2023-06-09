package rick_and_morty.eventbus

sealed interface BusEvent

data class NavigateToCharacterDetails(val characterId: Int) : BusEvent
object NavigateToEpisodes : BusEvent
object NavigateToCharacters : BusEvent
