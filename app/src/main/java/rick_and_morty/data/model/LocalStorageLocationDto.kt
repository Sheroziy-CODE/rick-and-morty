package rick_and_morty.data.model
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LocalStorageLocation (
    @PrimaryKey
    var name: String = "",
    var url: String = ""
): RealmObject()