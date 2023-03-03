package rick_and_morty.ui.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel(){

    private var initialLoad: Boolean = true
    private var page = 1;

    private var _characters = MutableStateFlow<List<CharacterResultsDto>>(emptyList())
    val characters: StateFlow<List<CharacterResultsDto>> = _characters

    init {
        getCharacters()
        initialLoad = false
    }

    fun getCharacters() {
        if (!initialLoad) {
            viewModelScope.launch {
                try {
                    val getCharacter = characterRepository.getCharacters(page)
                    _characters.update {
                        it + getCharacter
                    }
                        page += 1;
                    }
                    catch(error: Exception) {
                        Log.e("GetCharacterError", error.toString())
                    }
            }
        }
    }
}


