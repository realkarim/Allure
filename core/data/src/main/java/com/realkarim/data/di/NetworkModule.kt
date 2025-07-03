package com.realkarim.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.realkarim.data.BuildConfig
import com.realkarim.data.HEADER_INTERCEPTOR_TAG
import com.realkarim.data.LOGGING_INTERCEPTOR_TAG
import com.realkarim.data.OkHttpClientProvider
import com.realkarim.data.OkHttpClientProviderInterface
import com.realkarim.data.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClientProvider(): OkHttpClientProviderInterface {
        return OkHttpClientProvider()
    }

    fun provideOkHttpCallFactory(
        @Named(HEADER_INTERCEPTOR_TAG) headerInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR_TAG) okHttpLoggingInterceptor: Interceptor,
        okHttpClientProvider: OkHttpClientProviderInterface,
    ): OkHttpClient {
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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideServiceFactory(retrofit: Retrofit): ServiceFactory {
        return ServiceFactory(retrofit)
    }
}
