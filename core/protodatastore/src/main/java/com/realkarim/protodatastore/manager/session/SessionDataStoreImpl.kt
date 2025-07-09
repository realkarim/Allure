package com.realkarim.protodatastore.manager.session

import androidx.datastore.core.DataStore
import com.realkarim.proto.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// todo: Double check if first() is the best way to get the data.
class SessionDataStoreImpl(private val sessionDataStore: DataStore<Session>) :
  SessionDataStore {

  override suspend fun setAccessToken(accessToken: String) {
    sessionDataStore.updateData { currentSessionData ->
      currentSessionData.toBuilder().setAccessToken(accessToken).build()
    }
  }

  override suspend fun setRefreshToken(refreshToken: String) {
    sessionDataStore.updateData { currentSessionData ->
      currentSessionData.toBuilder().setRefreshToken(refreshToken).build()
    }
  }

  override suspend fun setUserIdToken(userId: String) {
    sessionDataStore.updateData { currentSessionData ->
      currentSessionData.toBuilder().setUserId(userId).build()
    }
  }

  override suspend fun setSession(accessToken: String, refreshToken: String, userId: String) {
    sessionDataStore.updateData { currentSessionData ->
      currentSessionData.toBuilder()
        .setUserId(userId)
        .setAccessToken(accessToken)
        .setRefreshToken(refreshToken).build()
    }
  }

  override suspend fun getAccessToken(): String {
    return sessionDataStore.data.first().accessToken
  }

  override fun getAccessTokenFlow(): Flow<String> {
    return sessionDataStore.data.map { session ->
      session.accessToken
    }
  }

  override suspend fun getRefreshToken(): String {
    return sessionDataStore.data.first().refreshToken
  }

  override fun getRefreshTokenFlow(): Flow<String> {
    return sessionDataStore.data.map { session ->
      session.refreshToken
    }
  }

  override suspend fun getUserId(): String {
    return sessionDataStore.data.first().userId
  }

  override fun getUserIdFlow(): Flow<String> {
    return sessionDataStore.data.map { session ->
      session.userId
    }
  }

  override suspend fun getSession(): Session {
    return sessionDataStore.data.first()
  }

  override fun getSessionFlow(): Flow<Session> {
    return sessionDataStore.data.map { session ->
      session
    }
  }
}
