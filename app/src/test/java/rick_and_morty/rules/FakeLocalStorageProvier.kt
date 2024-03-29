package rick_and_morty.rules

import io.realm.RealmObject
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.LocalStorageCharacters
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.LocalStorageEpisodes
import rick_and_morty.data.realm.LocalStorageInstance
import rick_and_morty.ui.characters.CharactersMapper.toLocalStorageCharacter
import rick_and_morty.ui.episodes.EpisodesMapper.toLocalStorageEpisode

class FakeLocalStorageInstance : LocalStorageInstance {

    private val fakeEpisodesDatabase = mutableListOf<LocalStorageEpisodes>()
    private val fakeCharactersDatabase = mutableListOf<LocalStorageCharacters>()

    var isFindAllCalled = false
    private var isSaveEpisodesToDatabaseCalled = false
    private var isSaveCharactersToDatabaseCalled = false

    fun setLocalStorageEpisodeResults(localStorageEpisodeResults: List<LocalStorageEpisodes>) {
        fakeEpisodesDatabase.clear()
        fakeEpisodesDatabase.addAll(localStorageEpisodeResults)
    }

    fun setLocalStorageCharacterResults(localStorageCharacterResults: List<LocalStorageCharacters>) {
        fakeCharactersDatabase.clear()
        fakeCharactersDatabase.addAll(localStorageCharacterResults)
    }

    override fun <T : RealmObject> findAll(clazz: Class<T>): List<T> {
        isFindAllCalled = true
        return when (clazz) {
            LocalStorageCharacters::class.java -> fakeCharactersDatabase as List<T>
            LocalStorageEpisodes::class.java -> fakeEpisodesDatabase as List<T>
            else -> emptyList()
        }
    }

    override fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        isSaveEpisodesToDatabaseCalled = true
        fakeEpisodesDatabase.clear()
        fakeEpisodesDatabase.addAll(episodesList.map { it.toLocalStorageEpisode() })
    }

    override fun clearEpisodesDatabase() {
        fakeEpisodesDatabase.clear()
    }

    override fun saveCharactersToDatabase(charactersList: List<CharacterResultsDto>) {
        isSaveCharactersToDatabaseCalled = true
        fakeCharactersDatabase.clear()
        fakeCharactersDatabase.addAll(charactersList.map { it.toLocalStorageCharacter() })
    }

    override fun clearCharactersDatabase() {
        fakeEpisodesDatabase.clear()
    }
}


