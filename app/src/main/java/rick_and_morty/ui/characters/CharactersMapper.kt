package rick_and_morty.ui.characters

import io.realm.RealmList
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.LocationDto
import rick_and_morty.data.model.OriginDto
import rick_and_morty.data.model.LocalStorageCharacters
import rick_and_morty.data.model.LocalStorageLocation
import rick_and_morty.data.model.LocalStorageOrigin

object CharactersMapper {

    fun CharacterResultsDto.toLocalStorageCharacter(): LocalStorageCharacters {
        return LocalStorageCharacters(
            created = created,
            episode = RealmList<String>().apply { addAll(this@toLocalStorageCharacter.episode)},
            gender = gender,
            id = id,
            image = image,
            locationDto = locationDto.toLocalStorageLocation(),
            name = name,
            originDto = originDto.toLocalStorageOrigin(),
            species = species,
            status = status,
            type = type,
            url = url
        )
    }

    fun LocationDto.toLocalStorageLocation(): LocalStorageLocation {
        return LocalStorageLocation(
            name = this.name,
            url = this.url
        )
    }

    fun OriginDto.toLocalStorageOrigin(): LocalStorageOrigin {
        return LocalStorageOrigin(
            name = this.name,
            url = this.url
        )
    }

    fun LocalStorageCharacters.toCharactersResultDto(): CharacterResultsDto {
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

    fun LocalStorageLocation.toLocationDto(): LocationDto {
        return LocationDto(
            name = this.name,
            url = this.url
        )
    }

    fun LocalStorageOrigin.toOriginDto(): OriginDto {
        return OriginDto(
            name = this.name,
            url = this.url
        )
    }
}
