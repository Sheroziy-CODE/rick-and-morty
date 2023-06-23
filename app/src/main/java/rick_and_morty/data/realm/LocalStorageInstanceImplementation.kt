package rick_and_morty.data.realm

import io.realm.Realm
import io.realm.RealmObject
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.LocalStorageCharacters
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.LocalStorageEpisodes
import rick_and_morty.ui.characters.CharactersMapper.toLocalStorageCharacter
import rick_and_morty.ui.episodes.EpisodesMapper.toLocalStorageEpisode

class LocalStorageInstanceImplementation(private val realm: Realm) : LocalStorageInstance {

    override fun <T : RealmObject> findAll(clazz: Class<T>): List<T> {
        return realm.where(clazz).findAll()
    }

    override fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        realm.executeTransaction { realm ->
            val localStorageEpisodes = episodesList.map { it.toLocalStorageEpisode() }
            realm.copyToRealmOrUpdate(localStorageEpisodes)
        }
    }

    override fun clearEpisodesDatabase() {
        realm.executeTransaction { realm ->
            realm.delete(LocalStorageEpisodes::class.java)
        }
    }

    override fun saveCharactersToDatabase(charactersList: List<CharacterResultsDto>) {
        realm.executeTransaction { realm ->
            val localStorageCharacters = charactersList.map { it.toLocalStorageCharacter() }
            realm.copyToRealmOrUpdate(localStorageCharacters)
        }
    }

    override fun clearCharactersDatabase() {
        realm.executeTransaction { realm ->
            realm.delete(LocalStorageCharacters::class.java)
        }
    }

}