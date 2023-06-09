import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rick_and_morty.data.model.episodes.EpisodesResponseDto
import rick_and_morty.data.model.episodes.EpisodesInfoDto
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.remote.RickAndMortyApiRemoteDataSource
import rick_and_morty.data.repository.EpisodesRepository

class EpisodeRepositoryTest {

    @Mock
    private lateinit var mockApiRemoteDataSource: RickAndMortyApiRemoteDataSource

    private lateinit var episodeRepository: EpisodesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        episodeRepository = EpisodesRepository(mockApiRemoteDataSource)
    }

    @Test
    fun `verify getEpisodes has been called`() = runBlocking {
        // Mock the response from the remote data source
        val mockResponse = EpisodesResponseDto(
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
        given(mockApiRemoteDataSource.fetchRickAndMortyEpisodesData(1)).willReturn(mockResponse)

        // Call the repository method
        episodeRepository.getEpisodes(1)

        // Verify the method call
        verify(mockApiRemoteDataSource).fetchRickAndMortyEpisodesData(1)

        // Get the result and assert
        val result = episodeRepository.getEpisodes(1)
        assertEquals(1, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Pilot", result[0].name)
        assertEquals("December 2, 2013", result[0].air_date)
        assertEquals("S01E01", result[0].episode)
        assertEquals(2, result[0].characters.size)
        assertEquals("https://rickandmortyapi.com/api/character/1", result[0].characters[0])
        assertEquals("https://rickandmortyapi.com/api/character/2", result[0].characters[1])
        assertEquals("https://rickandmortyapi.com/api/episode/1", result[0].url)
        assertEquals("2017-11-10T12:56:33.798Z", result[0].created)
    }
}

