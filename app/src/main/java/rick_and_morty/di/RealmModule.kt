package rick_and_morty.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import rick_and_morty.data.realm.LocalStorageInstanceImplementation
import rick_and_morty.data.realm.LocalStorageInstance
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(@ApplicationContext context: Context): Realm {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name("rickandmorty.realm")
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)

        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideRealmProvider(realm: Realm): LocalStorageInstance {
        return LocalStorageInstanceImplementation(realm)
    }

}

