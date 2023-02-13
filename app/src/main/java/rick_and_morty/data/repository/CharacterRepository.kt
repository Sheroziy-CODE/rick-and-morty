package rick_and_morty.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rick_and_morty.di.RickAndMortyApiRemoteDataSource
import javax.inject.Inject
import rick_and_morty.data.model.CharacterResultsDto


class CharacterRepository @Inject constructor(
    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource
){
    
   suspend fun getCharacters(page: Int): List<CharacterResultsDto> {
       return rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(page).results
    }


/*    suspend fun getRickAndMortyInfo(page: Int): List<InfoDto> {
        return characterApiService.fetchAllCharacters(page).info!!
    }*/
}
