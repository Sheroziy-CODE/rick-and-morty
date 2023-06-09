package rick_and_morty

import android.os.Bundle
import androidx.navigation.NavController
import com.example.rick_and_morty.R
import rick_and_morty.eventbus.BusEvent
import rick_and_morty.eventbus.NavigateToCharacterDetails
import rick_and_morty.eventbus.NavigateToCharacters
import rick_and_morty.eventbus.NavigateToEpisodes

fun NavController.handleNavigation(event: BusEvent) = when (event) {
    is NavigateToCharacterDetails -> {
        val bundle = Bundle().apply {
            putInt("characterID", event.characterId)
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
