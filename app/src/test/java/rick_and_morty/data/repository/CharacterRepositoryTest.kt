package rick_and_morty.data.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import rick_and_morty.data.model.RickAndMortyResponseDto
import rick_and_morty.di.RickAndMortyApiRemoteDataSource

class CharacterRepositoryTest {

    private val rickAndMortyApiRemoteDataSource: RickAndMortyApiRemoteDataSource = mock ()
    private val classToTest = CharacterRepository(rickAndMortyApiRemoteDataSource)
    private val rickAndMorty: RickAndMortyResponseDto = mock ()

    @Test
    fun `verify getCharacter has been called`(): Unit = runBlocking {

        given(rickAndMortyApiRemoteDataSource.fetchRickAndMortyData(1)).willReturn(rickAndMorty)

        classToTest.getCharacters(1)

        verify(rickAndMortyApiRemoteDataSource).fetchRickAndMortyData(1)
    }
}