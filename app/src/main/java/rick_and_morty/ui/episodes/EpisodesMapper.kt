package rick_and_morty.ui.episodes

import io.realm.RealmList
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.LocalStorageEpisodes

object EpisodesMapper {

    fun EpisodeResultDto.toLocalStorageEpisode(): LocalStorageEpisodes {
        return LocalStorageEpisodes(
            id = id,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = RealmList<String>().apply { addAll(this@toLocalStorageEpisode.characters) },
            url = url,
            created = created
        )
    }

    fun LocalStorageEpisodes.toEpisodesResultDto(): EpisodeResultDto {
        return EpisodeResultDto(
            id = id,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = characters,
            url = url,
            created = created
        )
    }
}
