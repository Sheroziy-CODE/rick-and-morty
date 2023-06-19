package rick_and_morty.ui.characters

import io.realm.RealmList
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.LocationDto
import rick_and_morty.data.model.OriginDto
import rick_and_morty.data.model.RealmCharacters
import rick_and_morty.data.model.RealmLocation
import rick_and_morty.data.model.RealmOrigin

object CharactersMapper {

    fun CharacterResultsDto.toRealmCharacter(): RealmCharacters {
        return RealmCharacters(
            created = created,
            episode = RealmList<String>().apply { addAll(this@toRealmCharacter.episode)},
            gender = gender,
            id = id,
            image = image,
            locationDto = locationDto.toRealmLocation(),
            name = name,
            originDto = originDto.toRealmOrigin(),
            species = species,
            status = status,
            type = type,
            url = url
        )
    }

    fun LocationDto.toRealmLocation(): RealmLocation {
        return RealmLocation(
            name = this.name,
            url = this.url
        )
    }

    fun OriginDto.toRealmOrigin(): RealmOrigin {
        return RealmOrigin(
            name = this.name,
            url = this.url
        )
    }

    fun RealmCharacters.toCharactersResultDto(): CharacterResultsDto {
        return CharacterResultsDto(
            created = created,
            episode = episode,
            gender = gender,
            id = id,
            image = image,
            locationDto = locationDto!!.toLocationDto(),
            name = name,
            originDto = originDto!!.toOriginDto(),
            species = species,
            status = status,
            type = type,
            url = url
        )
    }

    fun RealmLocation.toLocationDto(): LocationDto {
        return LocationDto(
            name = this.name,
            url = this.url
        )
    }

    fun RealmOrigin.toOriginDto(): OriginDto {
        return OriginDto(
            name = this.name,
            url = this.url
        )
    }
}
