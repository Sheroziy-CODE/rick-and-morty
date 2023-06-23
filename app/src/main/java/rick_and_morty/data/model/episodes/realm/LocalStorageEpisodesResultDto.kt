package rick_and_morty.data.model.episodes.realm

import io.realm.RealmObject
import io.realm.RealmList
import io.realm.annotations.PrimaryKey

open class LocalStorageEpisodes (
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var airDate: String = "",
    var episode: String = "",
    var characters: RealmList<String> = RealmList(),
    var url: String = "",
    var created: String = ""
) : RealmObject()