package rick_and_morty.ui.characters
import CharacterList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rick_and_morty.eventbus.EventBus
import rick_and_morty.handleNavigation
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    @Inject
    lateinit var eventBus: EventBus
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            val navController = findNavController()
            setContent {
                CharacterList()
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    eventBus.events.collect { event ->
                        navController.handleNavigation(event)
                    }
                }
            }
        }
    }
}