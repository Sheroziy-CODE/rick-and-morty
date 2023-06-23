import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import io.realm.RealmList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rick_and_morty.data.model.episodes.EpisodesResponseDto
import rick_and_morty.data.model.episodes.EpisodesInfoDto
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.LocalStorageEpisodes
import rick_and_morty.data.realm.LocalStorageInstance
import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource
import rick_and_morty.data.repository.EpisodesRepository


class EpisodeRepositoryTest {

    @Mock
    private lateinit var mockApiRemoteDataSource: RickAndMortyApiRemoteDataSource

    @Mock
    private lateinit var localStorageInstance: LocalStorageInstance

    private lateinit var episodeRepository: EpisodesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        episodeRepository = EpisodesRepository(mockApiRemoteDataSource, localStorageInstance)
    }

    private val mockResponse = EpisodesResponseDto(
        EpisodesInfoDto(51, 3, "https://rickandmortyapi.com/api/episode?page=2", null),
        listOf(
            EpisodeResultDto(
                1,
                "Pilot",
                "December 2, 2013",
                "S01E01",
                listOf("https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/2"),
                "https://rickandmortyapi.com/api/episode/1",
                "2017-11-10T12:56:33.798Z"
            )
        )
    )

    @Test
    fun `verify Get Episodes has been called`(): Unit = runBlocking {

        given(mockApiRemoteDataSource.fetchRickAndMortyEpisodesData(1)).willReturn(mockResponse)


        episodeRepository.getEpisodes(1)


        verify(mockApiRemoteDataSource).fetchRickAndMortyEpisodesData(1)
    }

    @Test
    fun `verify getEpisodesFromDatabase has been called`() = runBlocking {

        val mockLocalStorageEpisode = LocalStorageEpisodes().apply {
            id = 1
            name = "Pilot"
            airDate = "December 2, 2013"
            episode = "S01E01"
            characters = RealmList("https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/2")
            url = "https://rickandmortyapi.com/api/episode/1"
            created = "2017-11-10T12:56:33.798Z"
        }
        given(localStorageInstance.findAll(LocalStorageEpisodes::class.java)).willReturn(listOf(mockLocalStorageEpisode))


        val result = episodeRepository.getEpisodesFromDatabase()


        verify(localStorageInstance).findAll(LocalStorageEpisodes::class.java)


        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Pilot", result[0].name)
        assertEquals("December 2, 2013", result[0].airDate)
        assertEquals("S01E01", result[0].episode)
        assertEquals(2, result[0].characters.size)
        assertEquals("https://rickandmortyapi.com/api/character/1", result[0].characters[0])
        assertEquals("https://rickandmortyapi.com/api/character/2", result[0].characters[1])
        assertEquals("https://rickandmortyapi.com/api/episode/1", result[0].url)
        assertEquals("2017-11-10T12:56:33.798Z", result[0].created)
    }

    @Test
    fun `verify Get Episodes Info has been called`(): Unit = runBlocking {

        given(mockApiRemoteDataSource.fetchRickAndMortyEpisodesData(1)).willReturn(mockResponse)

        episodeRepository.getEpisodesInfo(1)

        verify(mockApiRemoteDataSource).fetchRickAndMortyEpisodesData(1)
    }

    @Test
    fun `verify saveEpisodesToDatabase has been called`() {

        episodeRepository.saveEpisodesToDatabase(mockResponse.results)

        verify(localStorageInstance).saveEpisodesToDatabase(mockResponse.results)
    }

    @Test
    fun `verify clearEpisodesDatabase has been called`() {
        episodeRepository.clearEpisodesDatabase()

        verify(localStorageInstance).clearEpisodesDatabase()
    }

}







