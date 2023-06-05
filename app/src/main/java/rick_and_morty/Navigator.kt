package rick_and_morty

import android.os.Bundle
import androidx.navigation.NavController
import com.example.rick_and_morty.R
import rick_and_morty.eventbus.BusEvent
import rick_and_morty.eventbus.NavigateToCharacterDetails

fun NavController.handleNavigation(event: BusEvent) = when (event) {
    is NavigateToCharacterDetails -> {
        val bundle = Bundle().apply {
            putInt("characterID", event.characterID)
        }
        navigate(R.id.viewCharacterDetails, bundle)
    }
}