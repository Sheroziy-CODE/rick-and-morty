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
import rick_and_morty.data.realm.RealmProvider
import rick_and_morty.data.repository.EpisodesRepository
import rick_and_morty.rules.CoroutineTestRule
import rick_and_morty.rules.FakeRealmProvider
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
    private val realmProvider: FakeRealmProvider = FakeRealmProvider()

    private val realmResults: List<RealmEpisodes> = mockEpisodes.map { it.toRealmEpisode() }

    @Before
    fun setup() {
        realmProvider.setRealmResults(realmResults)
    }


    private val classToTest by lazy { EpisodesViewModel(episodesRepository, realmProvider) }

    @Test
    fun `verify getEpisodes updates episodes flow with results`() = runTest {

        given(episodesRepository.getEpisodes(1)).willReturn(mockEpisodes)

        classToTest.getEpisodes()

        assertTrue(realmProvider.isFindAllCalled)
        verify(episodesRepository).getEpisodes(1)

        assertThat(classToTest.episodes.value.isLoading).isFalse()
        assertThat(classToTest.episodes.value.episodeResults).isEqualTo(mockEpisodes)
    }


    @Test
    fun `verify getEpisodes updates episodes flow with error`() = runTest {
        val errorMessage = "Failed to fetch episodes"
        given(episodesRepository.getEpisodes(1)).willThrow(RuntimeException(errorMessage))

        classToTest.getEpisodes()

        assertThat(classToTest.episodes.value.isLoading).isFalse()
        assertThat(classToTest.episodes.value.episodeResults).isEmpty()
        assertThat(classToTest.episodes.value.failure).isInstanceOf(RuntimeException::class.java)
        assertThat(classToTest.episodes.value.failure?.message).isEqualTo(errorMessage)
    }
}
