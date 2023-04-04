package rick_and_morty.ui.character_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rick_and_morty.data.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){


    private var _characterDetails = MutableStateFlow(CharacterDetailsUiState())
    val characterDetails: StateFlow<CharacterDetailsUiState> = _characterDetails

    init{
        val listItemId = savedStateHandle.get<Int>("characterID")
        getCharacterDetails(listItemId!!)
    }

    private fun getCharacterDetails(id: Int) {
        _characterDetails.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                try {
                    val getCharacterDetails = characterRepository.getCharacterDetails(id)
                    _characterDetails.update {
                        it.copy(isLoading = false, characterResultDetails = getCharacterDetails)
                    }
                }
                catch(error: Exception) {
                    _characterDetails.update {
                        it.copy(isLoading = false, failure = error)
                    }
                }
            }
    }
}



