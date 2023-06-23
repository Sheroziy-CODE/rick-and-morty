package rick_and_morty.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LocalStorageCharacters (
    @PrimaryKey
    var created: String = "",
    var episode: RealmList<String> = RealmList(),
    var gender: String = "",
    var id: Int = 0,
    var image: String = "",
    var locationDto: LocalStorageLocation? = null,
    var name: String = "",
    var originDto: LocalStorageOrigin? = null,
    var species: String = "",
    var status: String = "",
    var type: String = "",
    var url: String = ""
) : RealmObject()