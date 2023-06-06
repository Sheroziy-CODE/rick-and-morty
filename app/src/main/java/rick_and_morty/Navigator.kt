package rick_and_morty

import android.os.Bundle
import androidx.navigation.NavController
import com.example.rick_and_morty.R
import rick_and_morty.eventbus.BusEvent
import rick_and_morty.eventbus.NavigateToCharacterDetails
import rick_and_morty.eventbus.NavigateToCharacters
import rick_and_morty.eventbus.NavigateToEpisodes
import javax.inject.Inject
import javax.inject.Singleton

fun NavController.handleNavigation(event: BusEvent) = when (event) {
    is NavigateToCharacterDetails -> {
        val bundle = Bundle().apply {
            putInt("characterID", event.characterID)
        }
        navigate(R.id.viewCharacterDetails, bundle)
    }
    is NavigateToEpisodes -> {
        navigate(R.id.viewEpisodesFragment)
    }
    is NavigateToCharacters -> {
        navigate(R.id.viewCharactersFragment)
    }
}
