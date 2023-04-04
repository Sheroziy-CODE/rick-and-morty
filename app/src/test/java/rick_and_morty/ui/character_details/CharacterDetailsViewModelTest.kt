package rick_and_morty.ui.character_details

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.LocationDto
import rick_and_morty.data.model.OriginDto
import rick_and_morty.data.repository.CharacterRepository
import rick_and_morty.rules.CoroutineTestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {


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

    private val characterResultsDto =
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

    private val savedStateHandle = SavedStateHandle (mapOf("characterID" to 1))

    private val characterRepository: CharacterRepository = mock {
        onBlocking { it.getCharacterDetails(1) } doReturn (characterResultsDto)
        onBlocking { it.getCharacterDetails(2) } doReturn (characterResultsDto)
    }

    private val classToTest by lazy {
        CharacterDetailsViewModel(
            characterRepository,
            savedStateHandle
        )
    }

    @Test
    fun `return Empty When Init ViewModel`() = runTest {

        assertThat(classToTest.characterDetails.value.characterResultDetails).isNull()

    }
    @Test
    fun `return List When GetCharacterDetails Called`() = runTest {

        classToTest.getCharacterDetails(2)

        advanceUntilIdle()

        verify(characterRepository).getCharacterDetails(1)

        assertThat(classToTest.characterDetails.value.characterResultDetails).isEqualTo(characterResultsDto)
    }

    @Test
    fun `catch An Error`() = runTest {

        given(characterRepository.getCharacters(any())).willThrow(RuntimeException("Error"))

        classToTest.getCharacterDetails(0)

        advanceUntilIdle()

        assertThat(classToTest.characterDetails.value.characterResultDetails).isNull()
    }

}
