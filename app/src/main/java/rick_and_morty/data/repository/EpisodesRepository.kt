package rick_and_morty.data.repository

import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource
import javax.inject.Inject


class EpisodesRepository @Inject constructor(
    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource
) {
    suspend fun getEpisodes(page: Int) =
        rickAndMortyApiRemoteDataSource.fetchRickAndMortyEpisodesData(page).results

}
