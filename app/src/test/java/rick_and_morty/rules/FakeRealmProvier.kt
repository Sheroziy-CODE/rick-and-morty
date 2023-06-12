package rick_and_morty.rules

import io.realm.RealmObject
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes
import rick_and_morty.data.realm.RealmProvider
import rick_and_morty.ui.episodes.EpisodesMapper.toRealmEpisode

class FakeRealmProvider : RealmProvider {

    private val fakeDatabase = mutableListOf<RealmEpisodes>()

    var isFindAllCalled = false
    private var isSaveEpisodesToDatabaseCalled = false

    fun setRealmResults(realmResults: List<RealmEpisodes>) {
        fakeDatabase.clear()
        fakeDatabase.addAll(realmResults)
    }

    override fun <T : RealmObject> findAll(clazz: Class<T>): List<T> {
        isFindAllCalled = true
        return fakeDatabase as List<T>
    }

    override fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>) {
        isSaveEpisodesToDatabaseCalled = true
        fakeDatabase.clear()
        fakeDatabase.addAll(episodesList.map { it.toRealmEpisode() })
    }
}


