package com.realkarim.data.di

import com.realkarim.data.ACCESS_TOKEN_TAG
import com.realkarim.data.CLIENT_ID_TAG
import com.realkarim.data.LANGUAGE_TAG
import com.realkarim.data.USER_ID_TAG
import com.realkarim.protodatastore.manager.preference.PreferencesDataStore
import com.realkarim.protodatastore.manager.session.SessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import java.util.Locale
import java.util.UUID
import javax.inject.Named
import javax.inject.Singleton

// todo: Find a better way to handle this without runBlocking.

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

  @Provides
  @Singleton
  @Named(USER_ID_TAG)
  fun provideUserId(sessionDataStore: SessionDataStore): () -> String? {
    val userId = runBlocking { sessionDataStore.getUserId() }
    return { userId }
  }

  @Provides
  @Singleton
  @Named(LANGUAGE_TAG)
  fun provideLanguage(preferenceDataStore: PreferencesDataStore): () -> Locale {
    val language = runBlocking { preferenceDataStore.getLanguage() }
    return if (language.isNotEmpty()) {
      { Locale(language) }
    } else {
      { Locale.ENGLISH }
    }
  }

  @Provides
  @Singleton
  @Named(ACCESS_TOKEN_TAG)
  fun provideAccessToken(sessionDataStore: SessionDataStore): () -> String? {
    val accessToken = runBlocking { sessionDataStore.getAccessToken() }
    return { accessToken }
  }

  @Provides
  @Singleton
  @Named(CLIENT_ID_TAG)
  fun provideClientId(): String {
    return UUID.randomUUID().toString()
  }
}
