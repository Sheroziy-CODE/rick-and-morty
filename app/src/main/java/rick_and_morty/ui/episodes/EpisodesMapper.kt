package rick_and_morty.ui.episodes

import io.realm.RealmList
import rick_and_morty.data.model.episodes.EpisodesResultDto
import rick_and_morty.data.model.episodes.realm.RealmEpisodes

object EpisodesMapper {

    fun EpisodesResultDto.toRealmEpisode(): RealmEpisodes {
        return RealmEpisodes(
            id = this.id,
            name = this.name,
            air_date = this.air_date,
            episode = this.episode,
            characters = RealmList<String>().apply { addAll(this@toRealmEpisode.characters) },
            url = this.url,
            created = this.created
        )
    }

    fun RealmEpisodes.toEpisodesResultDto(): EpisodesResultDto {
        return EpisodesResultDto(
            id = this.id,
            name = this.name,
            air_date = this.air_date,
            episode = this.episode,
            characters = this.characters,
            url = this.url,
            created = this.created
        )
    }
}
