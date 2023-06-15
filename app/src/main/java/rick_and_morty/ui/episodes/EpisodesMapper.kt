package rick_and_morty.ui.episodes

import io.realm.RealmList
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes

object EpisodesMapper {

    fun EpisodeResultDto.toRealmEpisode(): RealmEpisodes {
        return RealmEpisodes(
            id = id,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = RealmList<String>().apply { addAll(this@toRealmEpisode.characters) },
            url = url,
            created = created
        )
    }

    fun RealmEpisodes.toEpisodesResultDto(): EpisodeResultDto {
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
