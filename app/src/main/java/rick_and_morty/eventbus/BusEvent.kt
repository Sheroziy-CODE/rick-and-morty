package rick_and_morty.eventbus

sealed interface BusEvent

data class NavigateToCharacterDetailsEvent(val characterId: Int) : BusEvent
object NavigateToEpisodesEvent : BusEvent
object NavigateToCharactersEvent : BusEvent
