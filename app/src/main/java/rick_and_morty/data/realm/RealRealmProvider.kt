package rick_and_morty.data.realm

import io.realm.Realm
import io.realm.RealmObject
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.ui.episodes.EpisodesMapper.toRealmEpisode

class RealRealmProvider(private val realm: Realm) : RealmProvider {

    override fun <T : RealmObject> findAll(clazz: Class<T>): List<T> {
        return realm.where(clazz).findAll()
    }

    override fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        realm.executeTransaction { realm ->
            val realmEpisodes = episodesList.map { it.toRealmEpisode() }
            realm.copyToRealmOrUpdate(realmEpisodes)
        }
    }
}