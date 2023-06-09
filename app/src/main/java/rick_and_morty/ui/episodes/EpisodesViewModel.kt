package rick_and_morty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes
import rick_and_morty.data.repository.EpisodesRepository
import rick_and_morty.ui.episodes.EpisodesMapper.toEpisodesResultDto
import rick_and_morty.ui.episodes.EpisodesMapper.toRealmEpisode
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository,
    private val realm: Realm, // Inject an instance of Realm
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
                var dbEpisodes = getEpisodesFromDatabase()
                if (dbEpisodes.isEmpty()) {
                    val apiEpisodes = episodesRepository.getEpisodes(page)
                    saveEpisodesToDatabase(apiEpisodes)
                    dbEpisodes = getEpisodesFromDatabase()
                    page += 1
                } else if (dbEpisodes.size < page * 20) {
                    val apiEpisodes = episodesRepository.getEpisodes(page)
                    saveEpisodesToDatabase(apiEpisodes)
                    dbEpisodes = getEpisodesFromDatabase()
                    page += 1
                }
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



    private fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        realm.executeTransaction { realm ->
            val realmEpisodes = episodesList.map { it.toRealmEpisode() }
            realm.copyToRealmOrUpdate(realmEpisodes)
        }
    }


    private fun getEpisodesFromDatabase(): List<EpisodeResultDto> {
        val realmEpisodes = realm.where(RealmEpisodes::class.java).findAll()
        return realmEpisodes.map { it.toEpisodesResultDto() }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }
}
