package rick_and_morty.ui.episodes.composables

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import rick_and_morty.BottomNavigationBar
import rick_and_morty.eventbus.EventBus

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodesList() {

        Text(text = "Episodes")
}