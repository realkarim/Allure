package com.realkarim.login.data.source

import com.realkarim.data.error.toDomain
import com.realkarim.data.result.OutCome
import com.realkarim.data.source.NetworkDataSource
import com.realkarim.login.data.request.LoginRequestBody
import com.realkarim.login.data.service.LoginService
import com.realkarim.login.domain.mapper.LoginMapper
import com.realkarim.login.domain.model.User

class LoginRemoteImpl(
  private val networkDataSource: NetworkDataSource<LoginService>,
  private val loginMapper: LoginMapper,
) : LoginRemote {
  override suspend fun login(loginRequestBody: LoginRequestBody): OutCome<User> {
    return networkDataSource.performRequest(
      request = { login(loginRequestBody).await() },
      onSuccess = { response, _ -> OutCome.success(loginMapper.toDomain(response)) },
      onError = { errorResponse, code -> OutCome.error(errorResponse.toDomain(code)) },
    )
  }
}
