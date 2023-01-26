package rick_and_morty.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rick_and_morty.data.api.ApiConstants
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)

object RickAndMortyApiModule {

private val moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): RickAndMortyApiRemoteDataSource {
        return builder
            .build()
            .create(RickAndMortyApiRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder{
        return  Retrofit.Builder()
            .baseUrl(ApiConstants.RICKANDMORTY_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

/*
    private val moshi =
        Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: CharacterApiService by lazy {
        retrofit.create(CharacterApiService::class.java)
    }

*/
}
