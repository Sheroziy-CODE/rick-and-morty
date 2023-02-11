package rick_and_morty.di

import retrofit2.http.GET
import retrofit2.http.Query
import rick_and_morty.data.model.RickAndMortyResponseDto
import rick_and_morty.data.api.ApiConstants

interface RickAndMortyApiRemoteDataSource {
    @GET("character")
    suspend fun fetchRickAndMortyData(@Query("page") page: Int): RickAndMortyResponseDto

}