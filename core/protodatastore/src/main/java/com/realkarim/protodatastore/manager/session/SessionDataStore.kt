package com.realkarim.protodatastore.manager.session

import com.realkarim.proto.Session
import kotlinx.coroutines.flow.Flow

interface SessionDataStore {
  // Setters

  suspend fun setAccessToken(accessToken: String)
  suspend fun setRefreshToken(refreshToken: String)
  suspend fun setUserIdToken(userId: String)
  suspend fun setSession(accessToken: String, refreshToken: String, userId: String)

  // Getters

  suspend fun getAccessToken(): String
  fun getAccessTokenFlow(): Flow<String>

  suspend fun getRefreshToken(): String
  fun getRefreshTokenFlow(): Flow<String>

  suspend fun getUserId(): String
  fun getUserIdFlow(): Flow<String>

  suspend fun getSession(): Session
  fun getSessionFlow(): Flow<Session>
}
