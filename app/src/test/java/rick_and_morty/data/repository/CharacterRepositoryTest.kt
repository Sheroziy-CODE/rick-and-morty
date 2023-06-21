package rick_and_morty.data.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import io.realm.RealmList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.RealmCharacters
import rick_and_morty.data.model.RealmLocation
import rick_and_morty.data.model.RealmOrigin
import rick_and_morty.data.model.RickAndMortyResponseDto
import rick_and_morty.data.realm.LocalStorageInstance
import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource

class CharacterRepositoryTest {

    private lateinit var mockApiRemoteDataSource: RickAndMortyApiRemoteDataSource
    private lateinit var localStorageInstance: LocalStorageInstance
    private lateinit var characterRepository: CharacterRepository

    private val mockResponse: RickAndMortyResponseDto = mock()
    private val characterResult: CharacterResultsDto = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockApiRemoteDataSource = mock()
        localStorageInstance = mock()
        characterRepository = CharacterRepository(mockApiRemoteDataSource, localStorageInstance)
    }

    @Test
    fun `verify getCharacters has been called`() = runBlocking {

        given(mockApiRemoteDataSource.fetchRickAndMortyData(1)).willReturn(mockResponse)
        given(mockResponse.results).willReturn(listOf(characterResult))

        characterRepository.getCharacters(1)

        verify(mockApiRemoteDataSource).fetchRickAndMortyData(1)
        assertThat(characterRepository.getCharacters(1)).isEqualTo(listOf(characterResult))
    }

    @Test
    fun `verify getCharacterDetails has been called`() = runBlocking {

        given(mockApiRemoteDataSource.fetchCharacterDetailsData(1)).willReturn(characterResult)

        characterRepository.getCharacterDetails(1)

        verify(mockApiRemoteDataSource).fetchCharacterDetailsData(1)
        assertThat(characterRepository.getCharacterDetails(1)).isEqualTo(characterResult)
    }

    @Test
    fun `verify getCharactersFromDatabase has been called`() = runBlocking {

        val mockRealmLocation = RealmLocation().apply {
            name = "Earth"
            url = "https://rickandmortyapi.com/api/location/1"
        }

        val mockRealmOrigin = RealmOrigin().apply {
            name = "Earth"
            url = "https://rickandmortyapi.com/api/location/1"
        }

        val mockRealmCharacter = RealmCharacters().apply {
            id = 1
            name = "Rick Sanchez"
            status = "Alive"
            species = "Human"
            type = ""
            gender = "Male"
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            created = "2017-11-04T18:50:21.651Z"
            episode = RealmList("https://rickandmortyapi.com/api/episode/1")
            locationDto = mockRealmLocation
            originDto = mockRealmOrigin
            url = "https://rickandmortyapi.com/api/character/1"
        }

        given(localStorageInstance.findAll(RealmCharacters::class.java)).willReturn(listOf(mockRealmCharacter))

        val result = characterRepository.getCharactersFromDatabase()

        verify(localStorageInstance).findAll(RealmCharacters::class.java)

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(1)
        assertThat(result[0].name).isEqualTo("Rick Sanchez")
        assertThat(result[0].status).isEqualTo("Alive")
        assertThat(result[0].species).isEqualTo("Human")
        assertThat(result[0].type).isEqualTo("")
        assertThat(result[0].gender).isEqualTo("Male")
        assertThat(result[0].image).isEqualTo("https://rickandmortyapi.com/api/character/avatar/1.jpeg")
    }


    @Test
    fun `verify saveCharactersToDatabase has been called`() {
        val charactersList = listOf(
            CharacterResultsDto(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                url = "https://rickandmortyapi.com/api/character/1",
                created = "2017-11-04T18:48:46.250Z",
                locationDto = mock(),
                originDto = mock()
            )
        )

        characterRepository.saveCharactersToDatabase(charactersList)

        verify(localStorageInstance).saveCharactersToDatabase(charactersList)
    }

    @Test
    fun `verify clearCharactersDatabase has been called`() {
        characterRepository.clearCharactersDatabase()

        verify(localStorageInstance).clearCharactersDatabase()
    }

}
