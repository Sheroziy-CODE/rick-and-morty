package rick_and_morty.rules

import io.realm.RealmObject
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.RealmCharacters
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes
import rick_and_morty.data.realm.LocalStorageInstance
import rick_and_morty.ui.characters.CharactersMapper.toRealmCharacter
import rick_and_morty.ui.episodes.EpisodesMapper.toRealmEpisode

class FakeRealmInstance : LocalStorageInstance {

    private val fakeEpisodesDatabase = mutableListOf<RealmEpisodes>()
    private val fakeCharactersDatabase = mutableListOf<RealmCharacters>()

    var isFindAllCalled = false
    private var isSaveEpisodesToDatabaseCalled = false
    private var isSaveCharactersToDatabaseCalled = false

    fun setRealmEpisodeResults(realmEpisodeResults: List<RealmEpisodes>) {
        fakeEpisodesDatabase.clear()
        fakeEpisodesDatabase.addAll(realmEpisodeResults)
    }

    fun setRealmCharacterResults(realmCharacterResults: List<RealmCharacters>) {
        fakeCharactersDatabase.clear()
        fakeCharactersDatabase.addAll(realmCharacterResults)
    }

    override fun <T : RealmObject> findAll(clazz: Class<T>): List<T> {
        isFindAllCalled = true
        return when (clazz) {
            RealmCharacters::class.java -> fakeCharactersDatabase as List<T>
            RealmEpisodes::class.java -> fakeEpisodesDatabase as List<T>
            else -> emptyList()
        }
    }

    override fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        isSaveEpisodesToDatabaseCalled = true
        fakeEpisodesDatabase.clear()
        fakeEpisodesDatabase.addAll(episodesList.map { it.toRealmEpisode() })
    }

    override fun clearEpisodesDatabase() {
        fakeEpisodesDatabase.clear()
    }

    override fun saveCharactersToDatabase(charactersList: List<CharacterResultsDto>) {
        isSaveCharactersToDatabaseCalled = true
        fakeCharactersDatabase.clear()
        fakeCharactersDatabase.addAll(charactersList.map { it.toRealmCharacter() })
    }

    override fun clearCharactersDatabase() {
        fakeEpisodesDatabase.clear()
    }
}


