package rick_and_morty.ui.characters

import androidx.test.core.app.ActivityScenario.launch
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import rick_and_morty.data.model.*
import rick_and_morty.data.repository.CharacterRepository
import rick_and_morty.eventbus.EventBus
import rick_and_morty.rules.CoroutineTestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val originDto = OriginDto(
        name = "Earth (C-137)",
        url = "https://rickandmortyapi.com/api/location/1"
    )

    private val locationDto = LocationDto(
        name = "Citadel of Ricks",
        url = "https://rickandmortyapi.com/api/location/3"
    )

    private val characterResultsDto = listOf(
        CharacterResultsDto(
            id = 1,
            name = "Chicko Micko",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            originDto = originDto,
            locationDto = locationDto,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/4",
                "https://rickandmortyapi.com/api/episode/5"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )
    )

    private var characterRepository: CharacterRepository = mock {
        onBlocking { it.getCharacters(1) } doReturn (characterResultsDto)
    }

    private var eventBus: EventBus = mock()

    private val classToTest by lazy { CharactersViewModel(characterRepository, eventBus) }

    @Test
    fun `return Empty List When Init ViewModel`() = runTest {
        given(characterRepository.getCharacters(any())).willReturn(emptyList())
        assertThat(classToTest.characters.value.characterResults).isEmpty()
    }

    @Test
    fun `return List When GetCharacter Called`() = runTest {

        classToTest.getCharacters()

        verify(characterRepository).getCharacters(1)

        assertThat(classToTest.characters.value.characterResults).isEqualTo(characterResultsDto)
        assertThat(classToTest.characters.value.isLoading).isFalse()
        assertThat(classToTest.characters.value.failure).isNull()
    }

    @Test
    fun `catch An Error`() = runTest {
        given(characterRepository.getCharacters(any())).willThrow(RuntimeException("Error"))

        classToTest.getCharacters()

        assertThat(classToTest.characters.value.characterResults).isEmpty()
        assertThat(classToTest.characters.value.isLoading).isFalse()
        assertThat(classToTest.characters.value.isFailure).isTrue()
    }

}