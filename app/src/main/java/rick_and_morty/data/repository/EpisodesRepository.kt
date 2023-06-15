package rick_and_morty.data.repository

import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes
import rick_and_morty.data.realm.RealmInstance
import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource
import rick_and_morty.ui.episodes.EpisodesMapper.toEpisodesResultDto
import javax.inject.Inject


class EpisodesRepository @Inject constructor(
    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource,
    private val realmInstance: RealmInstance,
) {
    suspend fun getEpisodes(page: Int) =
        rickAndMortyApiRemoteDataSource.fetchRickAndMortyEpisodesData(page).results

    fun getEpisodesFromDatabase(): List<EpisodeResultDto> {
        val realmEpisodes = realmInstance.findAll(RealmEpisodes::class.java)
        return realmEpisodes.map { it.toEpisodesResultDto() }
    }

    fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        realmInstance.saveEpisodesToDatabase(episodesList)
    }
}

