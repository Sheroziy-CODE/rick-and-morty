package rick_and_morty.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rick_and_morty.data.model.RickAndMortyResponseDto
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.episodes.EpisodeResponseDto

interface RickAndMortyApiRemoteDataSource {
    @GET("character")
    suspend fun fetchRickAndMortyData(@Query("page") page: Int): RickAndMortyResponseDto

    @GET("character/{character_id}")
    suspend fun fetchCharacterDetailsData(@Path("character_id") id: Int): CharacterResultsDto

    @GET("episode")
    suspend fun fetchRickAndMortyEpisodesData(@Query("page") page: Int): EpisodeResponseDto

}