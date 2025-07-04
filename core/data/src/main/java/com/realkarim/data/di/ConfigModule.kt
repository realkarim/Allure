package com.realkarim.data.di

import com.realkarim.data.ACCESS_TOKEN_TAG
import com.realkarim.data.CLIENT_ID_TAG
import com.realkarim.data.LANGUAGE_TAG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {
  @Provides
  @Singleton
  @Named(LANGUAGE_TAG)
  fun provideLanguage(): () -> Locale {
    return { Locale.ENGLISH } // todo get locale from user prefs later, move me to config module
  }

  @Provides
  @Singleton
  @Named(ACCESS_TOKEN_TAG)
  fun provideAccessToken(): () -> String? {
    return { "" } // todo get access token from user prefs later, move me to config module
  }

  @Provides
  @Singleton
  @Named(CLIENT_ID_TAG)
  fun provideClientId(): String {
    return "" // todo get client id from user prefs later, move me to config module
  }
}
