package rick_and_morty.data.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.RickAndMortyResponseDto
import rick_and_morty.di.RickAndMortyApiRemoteDataSource

class CharacterRepositoryTest {

    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource = mock ()
    private val classToTest = CharacterRepository(rickAndMortyApiRemoteDataSource)
    private val rickAndMorty: RickAndMortyResponseDto = mock ()
    private val characterResult: CharacterResultsDto = mock ()

    @Test
    fun `verify getCharacter has been called`(): Unit = runBlocking {

        given(rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(1)).willReturn(rickAndMorty)

        classToTest.getCharacters(1)

        verify(rickAndMortyApiRemoteDataSource).fetchRickAndMortyData(1)

    }


    @Test
    fun `verify getCharacterDetails has been called`(): Unit = runBlocking {

        given(rickAndMortyApiRemoteDataSource.fetchCharacterDetailsData(1)).willReturn(characterResult)

        classToTest.getCharacterDetails(1)

        verify(rickAndMortyApiRemoteDataSource).fetchCharacterDetailsData(1)

    }
}