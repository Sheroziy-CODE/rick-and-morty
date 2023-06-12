package rick_and_morty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import rick_and_morty.data.realm.RealRealmProvider
import rick_and_morty.data.realm.RealmProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideRealmProvider(realm: Realm): RealmProvider {
        return RealRealmProvider(realm)
    }
}

