package rick_and_morty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rick_and_morty.data.repository.CharacterRepository
import rick_and_morty.eventbus.EventBus
import rick_and_morty.eventbus.NavigateToCharacterDetailsEvent
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val eventBus: EventBus
) : ViewModel() {

    private var page = 1

    private val _characters = MutableStateFlow(CharactersUiState())
    val characters: StateFlow<CharactersUiState> = _characters

    init {
        getCharacters()
    }

    fun getCharacters() {
        _characters.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                var dbCharacters = characterRepository.getCharactersFromDatabase()
                val apiCharactersInfo = characterRepository.getCharactersInfo(page)

                if (dbCharacters.size < page * 20) {
                    val apiCharacters = characterRepository.getCharacters(page)
                    characterRepository.saveCharactersToDatabase(apiCharacters)
                    dbCharacters = characterRepository.getCharactersFromDatabase()
                }

                _characters.update {
                    it.copy(
                        isLoading = false,
                        characterResults = dbCharacters
                    )
                }

                if (apiCharactersInfo.next != null){
                    page += 1
                }

            } catch (error: Exception) {
                _characters.update {
                    it.copy(isLoading = false, failure = error)
                }
            }
        }
    }
    fun onCharacterSelected(characterID: Int) {
        eventBus.postEvent(NavigateToCharacterDetailsEvent(characterID))
    }

    fun refreshCharacters() {
        viewModelScope.launch {
            try {
                _characters.update { it.copy(isLoading = true) }

                characterRepository.clearCharactersDatabase()
                page = 1

                val dbCharacters = characterRepository.getCharacters(page)
                characterRepository.saveCharactersToDatabase(dbCharacters)

                _characters.update {
                    it.copy(
                        isLoading = false,
                        characterResults = dbCharacters
                    )
                }


            } catch (error: Exception) {
                _characters.update {
                    it.copy(isLoading = false, failure = error)
                }
            }
        }
    }
}




