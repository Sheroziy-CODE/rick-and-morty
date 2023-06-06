package rick_and_morty

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import rick_and_morty.eventbus.EventBus
import rick_and_morty.eventbus.NavigateToCharacters
import rick_and_morty.eventbus.NavigateToEpisodes


@Composable
fun BottomNavigationBar(navController: NavController, eventBus: EventBus) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = null) },
            label = { Text("Characters") },
            selected = currentRoute == "Characters",
            onClick = { eventBus.postEvent(NavigateToCharacters) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            label = { Text("Episodes") },
            selected = currentRoute == "Episodes",
            onClick = { eventBus.postEvent(NavigateToEpisodes) }
        )
    }
}


