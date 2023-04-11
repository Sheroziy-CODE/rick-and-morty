package rick_and_morty.ui.character_details


data class CharacterDetails(val image: String, val characterShortDetailsList: List<CharacterShortDetails>)
data class CharacterShortDetails(val title: String, val info: String)