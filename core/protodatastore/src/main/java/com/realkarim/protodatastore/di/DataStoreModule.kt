package com.realkarim.protodatastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.realkarim.proto.Preferences
import com.realkarim.proto.Session
import com.realkarim.protodatastore.factory.preferencesDataStore
import com.realkarim.protodatastore.factory.sessionDataStore
import com.realkarim.protodatastore.manager.preference.PreferencesDataStore
import com.realkarim.protodatastore.manager.preference.PreferencesDataStoreImpl
import com.realkarim.protodatastore.manager.session.SessionDataStore
import com.realkarim.protodatastore.manager.session.SessionDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

  @Provides
  @Singleton
  fun provideSessionDataStore(@ApplicationContext context: Context): DataStore<Session> {
    return context.sessionDataStore
  }

  @Provides
  @Singleton
  fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
    return context.preferencesDataStore
  }

  @Provides
  @Singleton
  fun provideSessionStoreManager(sessionDataStore: DataStore<Session>): SessionDataStore {
    return SessionDataStoreImpl(sessionDataStore)
  }

  @Provides
  @Singleton
  fun providePreferencesStoreManager(preferencesDataStore: DataStore<Preferences>): PreferencesDataStore {
    return PreferencesDataStoreImpl(preferencesDataStore)
  }
}
