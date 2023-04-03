package rick_and_morty.ui.characters
import CharacterList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rick_and_morty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            val navController = findNavController()
            setContent {
                CharacterList(onNavigateToCharacterDetails = {characterDetails ->
                    val bundle = Bundle().apply {
                        putInt("characterID", characterDetails.id)
                    }
                    navController.navigate(R.id.viewCharacterDetails, bundle)
                })
            }
        }
    }
}