package rick_and_morty.data.repository

import rick_and_morty.di.RickAndMortyApiRemoteDataSource
import javax.inject.Inject
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.event.BusEvent


class CharacterRepository @Inject constructor(
    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource
){
    
   suspend fun getCharacters(page: Int): BusEvent<List<CharacterResultsDto>> {
       return try {
           BusEvent.Success(list = rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(page).results)
       }
       catch (error: Exception){
           BusEvent.Error(exception = error)
       }
    }


/*    suspend fun getRickAndMortyInfo(page: Int): List<InfoDto> {
        return characterApiService.fetchAllCharacters(page).info!!
    }*/
}
