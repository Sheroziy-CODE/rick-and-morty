package rick_and_morty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rick_and_morty.data.event.BusEvent
import rick_and_morty.data.event.UIState
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel(){

    private var initialLoad: Boolean = true
    private var page = 1;

    private var _characters = MutableStateFlow(UIState())
    val characters: StateFlow<UIState> = _characters

    private var characterList = mutableListOf<CharacterResultsDto>()


    init {
        getCharacters()
        initialLoad = false
    }

    fun getCharacters() {
        _characters.update { it.copy(isLoading = true) }
        if (!initialLoad) {
            viewModelScope.launch {
                val getCharacter = characterRepository.getCharacters(page)
                _characters.update {
                    when(getCharacter){
                        is BusEvent.Success -> { getCharacter.list.let { characterList.addAll(it) }
                            it.copy(isLoading = false, isSuccess = characterList)
                        }
                        is BusEvent.Error -> it.copy(isLoading = false, isFailure = true, failureMessage = getCharacter.exception)
                    }
                }
                page += 1;
            }
        }
    }
}



