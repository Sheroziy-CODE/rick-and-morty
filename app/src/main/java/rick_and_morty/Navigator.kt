package rick_and_morty

import android.os.Bundle
import androidx.navigation.NavController
import com.example.rick_and_morty.R
import rick_and_morty.eventbus.BusEvent
import rick_and_morty.eventbus.NavigateToCharacterDetailsEvent
import rick_and_morty.eventbus.NavigateToCharactersEvent
import rick_and_morty.eventbus.NavigateToEpisodesEvent

fun NavController.handleNavigation(event: BusEvent) = when (event) {
    is NavigateToCharacterDetailsEvent -> {
        val bundle = Bundle().apply {
            putInt("characterID", event.characterId)
        }
        navigate(R.id.viewCharacterDetails, bundle)
    }
    is NavigateToEpisodesEvent -> {
        navigate(R.id.viewEpisodesFragment)
    }
    is NavigateToCharactersEvent -> {
        navigate(R.id.viewCharactersFragment)
    }
}
