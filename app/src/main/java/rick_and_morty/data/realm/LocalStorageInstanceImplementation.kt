package rick_and_morty.data.realm

import io.realm.Realm
import io.realm.RealmObject
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.LocalStorageCharacters
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.LocalStorageEpisodes
import rick_and_morty.ui.characters.CharactersMapper.toRealmCharacter
import rick_and_morty.ui.episodes.EpisodesMapper.toRealmEpisode

class LocalStorageInstanceImplementation(private val realm: Realm) : LocalStorageInstance {

    override fun <T : RealmObject> findAll(clazz: Class<T>): List<T> {
        return realm.where(clazz).findAll()
    }

    override fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        realm.executeTransaction { realm ->
            val realmEpisodes = episodesList.map { it.toRealmEpisode() }
            realm.copyToRealmOrUpdate(realmEpisodes)
        }
    }

    override fun clearEpisodesDatabase() {
        realm.executeTransaction { realm ->
            realm.delete(LocalStorageEpisodes::class.java)
        }
    }

    override fun saveCharactersToDatabase(charactersList: List<CharacterResultsDto>) {
        realm.executeTransaction { realm ->
            val realmCharacters = charactersList.map { it.toRealmCharacter() }
            realm.copyToRealmOrUpdate(realmCharacters)
        }
    }

    override fun clearCharactersDatabase() {
        realm.executeTransaction { realm ->
            realm.delete(LocalStorageCharacters::class.java)
        }
    }

}