package rick_and_morty.ui.episodes
import rick_and_morty.data.model.episodes.EpisodesResultDto

data class EpisodesUiState (
    val isLoading : Boolean = true,
    val episodeResults: List<EpisodesResultDto> = emptyList(),
    val failure: Exception? = null
)
{
    val isFailure: Boolean = failure != null
}