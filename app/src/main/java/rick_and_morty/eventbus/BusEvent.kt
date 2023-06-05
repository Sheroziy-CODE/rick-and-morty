package rick_and_morty.eventbus

sealed interface BusEvent

data class NavigateToCharacterDetails(val characterID: Int) : BusEvent
