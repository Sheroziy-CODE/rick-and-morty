package rick_and_morty.data.repository

import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.RealmCharacters
import rick_and_morty.data.realm.LocalStorageInstance
import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource
import rick_and_morty.ui.characters.CharactersMapper.toCharactersResultDto
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource,
    private val localStorageInstance: LocalStorageInstance,
) {

    suspend fun getCharacters(page: Int) =
        rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(page).results

    suspend fun getCharactersInfo(page: Int) =
        rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(page).infoDto


    suspend fun getCharacterDetails(id: Int) =
        rickAndMortyApiRemoteDataSource.fetchCharacterDetailsData(id)


    fun getCharactersFromDatabase(): List<CharacterResultsDto> {
        val realmEpisodes = localStorageInstance.findAll(RealmCharacters::class.java)
        return realmEpisodes.map { it.toCharactersResultDto() }
    }

    fun saveCharactersToDatabase(charactersList: List<CharacterResultsDto>) {
        localStorageInstance.saveCharactersToDatabase(charactersList)
    }

    fun clearCharactersDatabase() {
        localStorageInstance.clearCharactersDatabase()
    }
}
