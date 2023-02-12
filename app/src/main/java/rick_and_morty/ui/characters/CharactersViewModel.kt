package rick_and_morty.ui.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel(){

    var initialLoad: Boolean = true

    private var _characters = MutableStateFlow<List<CharacterResultsDto>>(emptyList())
    val characters = _characters.asStateFlow()

    fun getCharacters(page: Int) {
        try {
            viewModelScope.launch {
                characterRepository.getCharacters(page)
                    .collect{ characters ->
                        _characters.update {
                            it + characters
                        }
                    }
                initialLoad = false
            }
        }

        catch(error: Exception) {
            Log.i("GetCharacterError", error.toString())
        }

    }
}


