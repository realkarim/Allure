package com.realkarim.login.data.di

import com.google.gson.Gson
import com.realkarim.data.DISPATCHER_DEFAULT_TAG
import com.realkarim.data.ServiceFactory
import com.realkarim.data.USER_ID_TAG
import com.realkarim.data.source.NetworkDataSource
import com.realkarim.login.data.mapper.LoginMapper
import com.realkarim.login.data.mapper.LoginMapperImpl
import com.realkarim.login.data.service.LoginService
import com.realkarim.login.domain.source.LoginRemote
import com.realkarim.login.data.source.LoginRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginNetworkModule {

  @Provides
  @Singleton
  fun provideLoginServiceFactory(serviceFactory: ServiceFactory): LoginService {
    return serviceFactory.create(LoginService::class.java)
  }

  @Provides
  @Singleton
  fun provideNetworkDataSource(
    loginService: LoginService,
    gson: Gson,
    @Named(USER_ID_TAG) userIdProvider: () -> String,
  ): NetworkDataSource<LoginService> {
    return NetworkDataSource(loginService, gson, userIdProvider)
  }

  @Provides
  @Singleton
  fun provideLoginMapper(
    @Named(DISPATCHER_DEFAULT_TAG) coroutineDispatcher: CoroutineDispatcher,
  ): LoginMapper {
    return LoginMapperImpl(coroutineDispatcher)
  }

  @Provides
  @Singleton
  fun provideLoginRemote(
    networkDataSource: NetworkDataSource<LoginService>,
    loginMapper: LoginMapper,
  ): LoginRemote {
    return LoginRemoteImpl(networkDataSource, loginMapper)
  }
}
