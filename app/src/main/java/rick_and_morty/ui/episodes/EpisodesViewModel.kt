package rick_and_morty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rick_and_morty.data.repository.EpisodesRepository
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private var page = 1

    private val _episodes = MutableStateFlow(EpisodesUiState())
    val episodes: StateFlow<EpisodesUiState> = _episodes

    init {
        getEpisodes()
    }

    fun getEpisodes() {
        _episodes.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                var dbEpisodes = episodesRepository.getEpisodesFromDatabase()
                val apiEpisodesInfo = episodesRepository.getEpisodesInfo(page)

                if (dbEpisodes.size < page * 20) {
                    val apiEpisodes = episodesRepository.getEpisodes(page)
                    episodesRepository.saveEpisodesToDatabase(apiEpisodes)
                    dbEpisodes = episodesRepository.getEpisodesFromDatabase()
                }

                _episodes.update {
                    it.copy(
                        isLoading = false,
                        episodeResults = dbEpisodes
                    )
                }
                if (apiEpisodesInfo.next != null){
                    page += 1
                }
            } catch (error: Exception) {
                _episodes.update {
                    it.copy(isLoading = false, failure = error)
                }
            }
        }
    }

    fun refreshEpisodes() {
        viewModelScope.launch {
            try {
                _episodes.update { it.copy(isLoading = true) }

                page = 1
                val dbEpisodes = episodesRepository.getEpisodes(page)
                episodesRepository.clearEpisodesDatabase()
                episodesRepository.saveEpisodesToDatabase(dbEpisodes)

                _episodes.update {
                    it.copy(
                        isLoading = false,
                        episodeResults = dbEpisodes
                    )
                }
            } catch (error: Exception) {
                _episodes.update {
                    it.copy(isLoading = false, failure = error)
                }
            }
        }
    }
}
