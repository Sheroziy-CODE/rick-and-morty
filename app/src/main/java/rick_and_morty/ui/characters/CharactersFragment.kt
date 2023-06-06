package rick_and_morty.ui.characters
import CharacterList
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rick_and_morty.BottomNavigationBar
import rick_and_morty.eventbus.EventBus
import rick_and_morty.ui.theme.RickandmortyTheme
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    @Inject lateinit var eventBus: EventBus

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RickandmortyTheme {
                    val navController = rememberNavController()

                    Surface(color = MaterialTheme.colorScheme.primary) {
                        Scaffold(
                            bottomBar = { BottomNavigationBar(navController, eventBus) }
                        ) {
                            CharacterList()
                        }
                    }
                }
            }
        }
    }
}
