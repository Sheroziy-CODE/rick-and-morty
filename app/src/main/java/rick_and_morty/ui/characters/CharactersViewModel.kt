package rick_and_morty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rick_and_morty.data.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
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
                val getCharacter = characterRepository.getCharacters(page)
                _characters.update {
                    it.copy(
                        isLoading = false,
                        characterResults = if (getCharacter != null)
                            it.characterResults + getCharacter
                        else it.characterResults
                    )
                }
                page += 1
            } catch (error: Exception) {
                _characters.update {
                    it.copy(isLoading = false, failure = error)
                }
            }
        }
    }
}




