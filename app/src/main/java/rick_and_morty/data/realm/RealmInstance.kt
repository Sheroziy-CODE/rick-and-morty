package rick_and_morty.data.realm

import io.realm.RealmObject
import rick_and_morty.data.model.episodes.EpisodeResultDto

interface RealmInstance {
    fun <T : RealmObject> findAll(clazz: Class<T>): List<T>
    fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>)
    fun clearEpisodesDatabase()
}