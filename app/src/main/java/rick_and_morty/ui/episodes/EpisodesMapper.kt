package rick_and_morty.ui.episodes

import io.realm.RealmList
import rick_and_morty.data.model.episodes.EpisodeResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes

object EpisodesMapper {

    fun EpisodeResultDto.toRealmEpisode(): RealmEpisodes {
        return RealmEpisodes(
            id = this.id,
            name = this.name,
            airDate = this.airDate,
            episode = this.episode,
            characters = RealmList<String>().apply { addAll(this@toRealmEpisode.characters) },
            url = this.url,
            created = this.created
        )
    }

    fun RealmEpisodes.toEpisodesResultDto(): EpisodeResultDto {
        return EpisodeResultDto(
            id = this.id,
            name = this.name,
            airDate = this.airDate,
            episode = this.episode,
            characters = this.characters,
            url = this.url,
            created = this.created
        )
    }
}
