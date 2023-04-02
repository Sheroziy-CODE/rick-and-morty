package rick_and_morty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rick_and_morty.data.event.UIState
import rick_and_morty.data.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel(){


    private var _characters = MutableStateFlow(UIState())
    val characters: StateFlow<UIState> = _characters


    init {
        getCharacterDetails(1)
    }

    fun getCharacterDetails(id: Int) {
        _characters.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                try {
                    val getCharacterDetails = characterRepository.getCharacterDetails(id)
                    _characters.update {
                        it.copy(isLoading = false, isSuccessDetails = getCharacterDetails)
                    }
                }
                catch(error: Exception) {
                    _characters.update {
                        it.copy(isLoading = false, isFailure = true, failureMessage = error)
                    }
                }
            }
    }
}



