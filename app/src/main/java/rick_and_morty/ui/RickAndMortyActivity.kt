package rick_and_morty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.rick_and_morty.R
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.launch
import rick_and_morty.eventbus.EventBus
import rick_and_morty.handleNavigation
import rick_and_morty.utils.NetworkUtils
import javax.inject.Inject

@AndroidEntryPoint
class RickAndMortyActivity : AppCompatActivity() {

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var realm: Realm

    @Inject

    lateinit var networkUtils: NetworkUtils

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (networkUtils.isInternetAvailable())
        {
            realm.executeTransactionAsync { it.deleteAll() }
        }

        setContentView(R.layout.activity_character)

        setupNavController()
        subscribeToNavigationEvents()
    }

    private fun setupNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private fun subscribeToNavigationEvents() {
        lifecycleScope.launch {
            eventBus.events.collect() { event ->
                navHostFragment.navController.handleNavigation(event)
            }
        }
    }
}
