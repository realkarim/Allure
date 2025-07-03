package com.realkarim.data

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

// Header names
const val AUTHORIZATION_HEADER = "Authorization"
const val ACCEPT_HEADER = "Accept"
const val CONTENT_TYPE_HEADER = "Content-Type"
const val ACCEPT_LANGUAGE_HEADER = "Accept-Language"
const val CLIENT_ID_HEADER = "Client-Id"

// Header values
const val JSON = "application/json"
const val ARABIC_LANGUAGE = "ar-EG"
const val ENGLISH_LANGUAGE = "en-US"

class HeaderInterceptor(
  private val clientId: String,
  private val accessTokenProvider: () -> String?,
  private val languageProvider: () -> Locale,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val requestBuilder = request.newBuilder()

    val language = if (languageProvider() == Locale.ENGLISH) {
      ENGLISH_LANGUAGE
    } else {
      ARABIC_LANGUAGE
    }

    requestBuilder
      .header(CLIENT_ID_HEADER, clientId)
      .header(ACCEPT_HEADER, JSON)
      .header(CONTENT_TYPE_HEADER, JSON)
      .header(ACCEPT_LANGUAGE_HEADER, language)

    accessTokenProvider()?.let {
      requestBuilder.header(AUTHORIZATION_HEADER, "Bearer $it")
    }

    return chain.proceed(requestBuilder.build())
  }
}
