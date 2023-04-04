package rick_and_morty.data.repository

import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource
) {

    suspend fun getCharacters(page: Int) =
        rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(page).results


    suspend fun getCharacterDetails(id: Int) =
        rickAndMortyApiRemoteDataSource.fetchCharacterDetailsData(id)
}
