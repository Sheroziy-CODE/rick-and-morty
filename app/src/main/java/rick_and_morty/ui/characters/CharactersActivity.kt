package rick_and_morty.ui.characters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rick_and_morty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : AppCompatActivity(R.layout.activity_character) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, CharactersFragment())
                .commit()
        }
    }
}