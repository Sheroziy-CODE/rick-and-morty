import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rick_and_morty.data.model.*
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes
import rick_and_morty.data.realm.RealmInstance
import rick_and_morty.data.repository.EpisodesRepository
import rick_and_morty.rules.CoroutineTestRule
import rick_and_morty.rules.FakeRealmInstance
import rick_and_morty.ui.episodes.EpisodesMapper.toRealmEpisode
import rick_and_morty.ui.episodes.EpisodesViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class EpisodesViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val mockEpisodes = listOf(
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

    private val episodesRepository: EpisodesRepository = mock()
    @Before
    fun setup() {
        runBlocking {
            whenever(episodesRepository.getEpisodesFromDatabase()).thenReturn(mockEpisodes)
        }
    }

    private val classToTest by lazy { EpisodesViewModel(episodesRepository) }

    @Test
    fun `verify getEpisodes updates episodes flow with results`() = runBlocking {
        whenever(episodesRepository.getEpisodes(1)).thenReturn(mockEpisodes)

        classToTest.getEpisodes()

        verify(episodesRepository).getEpisodes(1)

        assertThat(classToTest.episodes.value.isLoading).isFalse()
        assertThat(classToTest.episodes.value.episodeResults).isEqualTo(mockEpisodes)
    }


    @Test
    fun `verify getEpisodes updates episodes flow with error`() = runTest {
        val errorMessage = "Failed to fetch episodes"
        whenever(episodesRepository.getEpisodes(1)).thenThrow(RuntimeException(errorMessage))

        classToTest.getEpisodes()

        assertThat(classToTest.episodes.value.isLoading).isFalse()
        assertThat(classToTest.episodes.value.episodeResults).isEmpty()
        assertThat(classToTest.episodes.value.failure).isInstanceOf(RuntimeException::class.java)
        assertThat(classToTest.episodes.value.failure?.message).isEqualTo(errorMessage)
    }
}
