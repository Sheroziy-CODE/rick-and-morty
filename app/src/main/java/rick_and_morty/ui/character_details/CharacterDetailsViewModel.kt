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


    private val _characterDetails = MutableStateFlow(CharacterDetailsUiState())
    val characterDetails: StateFlow<CharacterDetailsUiState> = _characterDetails

    init{
        val listItemId = savedStateHandle.get<Int>("characterID")
        getCharacterDetails(listItemId!!)
    }

    fun getCharacterDetails(id: Int) {
        _characterDetails.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                try {
                    val getCharacterDetails = characterRepository.getCharacterDetails(id)
                    _characterDetails.update {
                        it.copy(isLoading = false, characterResultDetails = CharacterDetails(
                            getCharacterDetails.image,
                            getCharacterDetailsList(
                                getCharacterDetails.name,
                                getCharacterDetails.locationDto.name,
                                getCharacterDetails.species,
                                getCharacterDetails.created,
                                getCharacterDetails.gender,
                                getCharacterDetails.originDto.name,
                                getCharacterDetails.status)
                        ))
                    }
                }
                catch(error: Exception) {
                    _characterDetails.update {
                        it.copy(isLoading = false, failure = error)
                    }
                }
            }
    }
    private fun getCharacterDetailsList(
        name: String,
        location: String,
        species: String,
        created: String,
        gender: String,
        originDto: String,
        status: String,
    ): List<CharacterShortDetails> {
        return listOf(
            CharacterShortDetails("Name", name),
            CharacterShortDetails("Last known location", location),
            CharacterShortDetails("Species", species),
            CharacterShortDetails("Created", created.substring(0, 10)),
            CharacterShortDetails("Gender", gender),
            CharacterShortDetails("Origin", originDto),
            CharacterShortDetails("Status", status)
        )
    }
}



