package rick_and_morty.ui.characters

import androidx.test.core.app.ActivityScenario.launch
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rick_and_morty.data.model.*
import rick_and_morty.data.repository.CharacterRepository
import rick_and_morty.eventbus.EventBus
import rick_and_morty.eventbus.NavigateToCharacterDetailsEvent
import rick_and_morty.rules.CoroutineTestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val mockCharacters = listOf(
        CharacterResultsDto(
            "1",
            listOf("https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/2"),
            "Male",
            1,
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            LocationDto("Earth (Replacement Dimension)", ""),
            "Rick Sanchez",
            OriginDto("Earth (C-137)", ""),
            "Alive",
            "Human",
            "",
            ""
        )
    )

    private val charactersRepository: CharacterRepository = mock()
    private val eventBus: EventBus = mock()

    @Before
    fun setup() {
        runBlocking {
            whenever(charactersRepository.getCharactersFromDatabase()).thenReturn(mockCharacters)
        }
    }

    private val classToTest by lazy { CharactersViewModel(charactersRepository, eventBus) }

    @Test
    fun `verify getCharacters updates characters flow with results`() = runBlocking {
        whenever(charactersRepository.getCharacters(1)).thenReturn(mockCharacters)

        classToTest.getCharacters()

        verify(charactersRepository, atLeast(1)).getCharacters(1)

        assertThat(classToTest.characters.value.isLoading).isFalse()
        assertThat(classToTest.characters.value.characterResults).isEqualTo(mockCharacters)
    }

    @Test
    fun `verify getCharacters updates characters flow with error`() = runBlocking {
        val errorMessage = "Failed to fetch characters"
        whenever(charactersRepository.getCharacters(1)).thenThrow(RuntimeException(errorMessage))

        classToTest.getCharacters()

        assertThat(classToTest.characters.value.isLoading).isFalse()
        assertThat(classToTest.characters.value.characterResults).isEmpty()
        assertThat(classToTest.characters.value.failure).isInstanceOf(RuntimeException::class.java)
        assertThat(classToTest.characters.value.failure?.message).isEqualTo(errorMessage)
    }

    @Test
    fun `verify refreshCharacters clears database and fetches characters again`() = runBlocking {
        whenever(charactersRepository.getCharacters(1)).thenReturn(mockCharacters)

        classToTest.refreshCharacters()

        verify(charactersRepository).clearCharactersDatabase()
        verify(charactersRepository, times(2)).getCharacters(1)
        verify(charactersRepository, times(2)).saveCharactersToDatabase(mockCharacters)

        assertThat(classToTest.characters.value.isLoading).isFalse()
        assertThat(classToTest.characters.value.characterResults).isEqualTo(mockCharacters)
    }

    @Test
    fun `verify refreshCharacters updates characters flow with error`() = runBlocking {
        val errorMessage = "Failed to refresh characters"
        whenever(charactersRepository.getCharacters(1)).thenThrow(RuntimeException(errorMessage))

        classToTest.refreshCharacters()

        assertThat(classToTest.characters.value.isLoading).isFalse()
        assertThat(classToTest.characters.value.characterResults).isEmpty()
        assertThat(classToTest.characters.value.failure).isInstanceOf(RuntimeException::class.java)
        assertThat(classToTest.characters.value.failure?.message).isEqualTo(errorMessage)
    }
    @Test
    fun `post NavigateToCharacterDetailsEvent when onCharacterSelected is called`() = runTest {
        val characterID = 1
        val eventCaptor = argumentCaptor<NavigateToCharacterDetailsEvent>()
        doNothing().`when`(eventBus).postEvent(eventCaptor.capture())

        classToTest.onCharacterSelected(characterID)

        val postedEvent = eventCaptor.firstValue
        assertThat(postedEvent.characterId).isEqualTo(characterID)
        assertThat(postedEvent).isInstanceOf(NavigateToCharacterDetailsEvent::class.java)
    }
}


