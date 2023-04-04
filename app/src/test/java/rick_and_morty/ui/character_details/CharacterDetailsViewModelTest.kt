package rick_and_morty.ui.character_details

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
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
import rick_and_morty.ui.characters.CharactersViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {

}