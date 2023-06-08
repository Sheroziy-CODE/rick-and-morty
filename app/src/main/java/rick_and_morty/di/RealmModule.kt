package rick_and_morty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}
