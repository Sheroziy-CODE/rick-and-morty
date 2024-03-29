package rick_and_morty.ui.character_details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
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
class CharacterShortDetailsViewModelTest {


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

    private val expectedCharacterShortDetailsLists = CharacterDetails(
        characterResultsDto.image,
        listOf(
            CharacterShortDetails("Name", "Chicko Micko"),
            CharacterShortDetails("Last known location", "Citadel of Ricks"),
            CharacterShortDetails("Species", "Human"),
            CharacterShortDetails("Created", "2017-11-04"),
            CharacterShortDetails("Gender", "Male"),
            CharacterShortDetails("Origin", "Earth (C-137)"),
            CharacterShortDetails("Status", "Alive"))
    )
    private val expectedEmptyCharacterShortDetailsLists = null

    private val savedStateHandle = SavedStateHandle (mapOf("characterID" to 1))

    private val characterRepository: CharacterRepository = mock {
        onBlocking { it.getCharacterDetails(1) } doReturn (characterResultsDto)
    }

    private val classToTest by lazy {
        CharacterDetailsViewModel(
            characterRepository,
            savedStateHandle
        )
    }
    @Test
    fun `return List When GetCharacterDetails Called`() = runTest {

        classToTest

        advanceUntilIdle()

        verify(characterRepository).getCharacterDetails(1)

        assertThat(classToTest.characterDetails.value.characterResultDetails).isEqualTo(expectedCharacterShortDetailsLists)
        assertThat(classToTest.characterDetails.value.isLoading).isFalse()
        assertThat(classToTest.characterDetails.value.failure).isNull()
    }

    @Test
    fun `getCharacterDetailsList returns expected list`() = runTest {

        val characterDetailsList = classToTest.characterDetails.value.characterResultDetails
        assertThat(characterDetailsList).isEqualTo(expectedCharacterShortDetailsLists)
    }

    @Test
    fun `catch An Error`() = runTest {

        given(characterRepository.getCharacterDetails(any())).willThrow(RuntimeException("Error"))

        classToTest.getCharacterDetails(0)

        assertThat(classToTest.characterDetails.value.characterResultDetails).isEqualTo(expectedEmptyCharacterShortDetailsLists)
        assertThat(classToTest.characterDetails.value.isLoading).isFalse()
        assertThat(classToTest.characterDetails.value.isFailure).isTrue()
    }

}
