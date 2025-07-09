package com.realkarim.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.realkarim.data.AUTHENTICATION_INTERCEPTOR_TAG
import com.realkarim.data.BuildConfig
import com.realkarim.data.CHUCKER_INTERCEPTOR_TAG
import com.realkarim.data.CONNECTIVITY_INTERCEPTOR_TAG
import com.realkarim.data.DISPATCHER_IO_TAG
import com.realkarim.data.HEADER_INTERCEPTOR_TAG
import com.realkarim.data.LOGGING_INTERCEPTOR_TAG
import com.realkarim.data.OkHttpClientProvider
import com.realkarim.data.OkHttpClientProviderImpl
import com.realkarim.data.connectivity.NetworkMonitor
import com.realkarim.data.interceptor.AUTHORIZATION_HEADER
import com.realkarim.data.interceptor.AuthenticationInterceptor
import com.realkarim.data.interceptor.CLIENT_ID_HEADER
import com.realkarim.data.interceptor.ConnectivityInterceptor
import com.realkarim.data.interceptor.HeaderInterceptor
import com.realkarim.protodatastore.manager.session.SessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InterceptorModule {
  @Provides
  @Singleton
  @Named(HEADER_INTERCEPTOR_TAG)
  fun provideHeaderInterceptor(
    @Named("ClientId") clientId: String,
    @Named("Language") languageProvider: () -> Locale,
  ): Interceptor {
    return HeaderInterceptor(
      clientId,
      languageProvider,
    )
  }

  @Provides
  @Singleton
  @Named(AUTHENTICATION_INTERCEPTOR_TAG)
  fun provideAuthenticationInterceptor(
    sessionDataStore: SessionDataStore,
    @Named(DISPATCHER_IO_TAG) coroutineDispatcher: CoroutineDispatcher,
  ): Interceptor {
    return AuthenticationInterceptor(
      sessionDataStore,
      coroutineDispatcher,
    )
  }

  @Provides
  @Singleton
  @Named(CONNECTIVITY_INTERCEPTOR_TAG)
  fun provideConnectivityInterceptor(
    networkMonitorInterface: NetworkMonitor,
  ): Interceptor {
    return ConnectivityInterceptor(
      networkMonitorInterface,
    )
  }

  @Provides
  @Singleton
  @Named(LOGGING_INTERCEPTOR_TAG)
  fun provideOkHttpLoggingInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
    if (!BuildConfig.DEBUG) {
      interceptor.redactHeader(CLIENT_ID_HEADER) // redact any header that contains sensitive data.
      interceptor.redactHeader(AUTHORIZATION_HEADER) // redact any header that contains sensitive data.
    }
    return interceptor
  }

  @Provides
  @Singleton
  fun provideOkHttpClientProvider(): OkHttpClientProvider {
    return OkHttpClientProviderImpl()
  }

  fun provideOkHttpCallFactory(
    @Named(HEADER_INTERCEPTOR_TAG) headerInterceptor: Interceptor,
    @Named(LOGGING_INTERCEPTOR_TAG) okHttpLoggingInterceptor: Interceptor,
    okHttpClientProvider: OkHttpClientProvider,
  ): Call.Factory {
    return okHttpClientProvider.getOkHttpClient(BuildConfig.PIN_CERTIFICATE)
      .addInterceptor(okHttpLoggingInterceptor)
      .addInterceptor(headerInterceptor)
      .retryOnConnectionFailure(true)
      .followRedirects(false)
      .followSslRedirects(false)
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .writeTimeout(60, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Singleton
  @Named(CHUCKER_INTERCEPTOR_TAG)
  fun provideChuckerInterceptor(@ApplicationContext context: Context): Interceptor {
    return ChuckerInterceptor.Builder(context)
      // The previously created Collector
      .collector(
        ChuckerCollector(
          context = context,
          showNotification = true,
          retentionPeriod = RetentionManager.Period.ONE_HOUR,
        ),
      )
      // The max body content length in bytes, after this responses will be truncated.
      .maxContentLength(250_000L)
      // List of headers to replace with ** in the Chucker UI
      .redactHeaders(AUTHORIZATION_HEADER)
      // Read the whole response body even when the client does not consume the response completely.
      // This is useful in case of parsing errors or when the response body
      // is closed before being read like in Retrofit with Void and Unit types.
      .alwaysReadResponseBody(true)
      // Use decoder when processing request and response bodies. When multiple decoders are installed they
      // are applied in an order they were added.
      // Controls Android shortcut creation.
      .createShortcut(true)
      .build()
  }
}
