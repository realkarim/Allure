package com.realkarim.data.interceptor

import com.realkarim.data.response.TokenResponse
import com.realkarim.data.service.SessionService
import com.realkarim.data.source.DataSource.Companion.UNAUTHORISED
import com.realkarim.protodatastore.manager.session.SessionDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
  private val sessionDataStore: SessionDataStore,
  private val coroutineDispatcher: CoroutineDispatcher,
) : Interceptor {

  // Using field injection instead of constructor injection to avoid circular dependency.
  @Inject
  lateinit var sessionService: SessionService

  private val mutex = Mutex()
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val accessToken =
      runBlocking(coroutineDispatcher) { sessionDataStore.getAccessToken() }

    val authenticatedRequest =
      request.newBuilder().header(AUTHORIZATION_HEADER, "Bearer $accessToken").build()

    val response = chain.proceed(authenticatedRequest)

    if (response.code != UNAUTHORISED) {
      // your access token is valid you can resume hitting APIs
      return response
    }

    // todo: Check if runBlocking could be avoided here.
    val tokenResponse: TokenResponse? = runBlocking {
      mutex.withLock {
        val tokenResponse = getUpdatedToken().await()
        tokenResponse.body().also {
          sessionDataStore.setAccessToken(it?.accessToken ?: "")
          sessionDataStore.setRefreshToken(it?.refreshToken ?: "")
        }
      }
    }

    return if (tokenResponse?.accessToken != null) {
      response.close()

      // Retry the original request with the new token
      val authenticatedRequest =
        request.newBuilder()
          .header(AUTHORIZATION_HEADER, "Bearer ${tokenResponse.accessToken}").build()

      val response = chain.proceed(authenticatedRequest)

      response
    } else {
      response
    }
  }

  private suspend fun getUpdatedToken(): Deferred<retrofit2.Response<TokenResponse>> {
    val refreshToken = sessionDataStore.getRefreshToken()
    return withContext(coroutineDispatcher) {
      sessionService.getTokens(refreshToken)
    }
  }
}
