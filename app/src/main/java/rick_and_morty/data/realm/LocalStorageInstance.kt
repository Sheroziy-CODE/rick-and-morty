package rick_and_morty.data.realm

import io.realm.RealmObject
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.episodes.EpisodeResultDto

interface LocalStorageInstance {
    fun <T : RealmObject> findAll(clazz: Class<T>): List<T>
    fun saveEpisodesToDatabase(episodesList: List<EpisodeResultDto>)
    fun clearEpisodesDatabase()
    fun saveCharactersToDatabase(charactersList: List<CharacterResultsDto>)
    fun clearCharactersDatabase()
}