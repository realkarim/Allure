package com.realkarim.login.data.source

import com.realkarim.data.mapper.toDomain
import com.realkarim.data.source.NetworkDataSource
import com.realkarim.domain.result.OutCome
import com.realkarim.login.data.mapper.LoginMapper
import com.realkarim.login.data.request.LoginRequestBody
import com.realkarim.login.data.service.LoginService
import com.realkarim.login.domain.model.User
import com.realkarim.login.domain.source.LoginRemote

class LoginRemoteImpl(
  private val networkDataSource: NetworkDataSource<LoginService>,
  private val loginMapper: LoginMapper,
) : LoginRemote {
  override suspend fun login(username: String, password: String): OutCome<User> {
    return networkDataSource.performRequest(
      request = {
        login(
          LoginRequestBody(username = username, password = password),
        ).await()
      },
      onSuccess = { response, _ -> OutCome.success(loginMapper.toDomain(response)) },
      onError = { errorResponse, code -> OutCome.error(errorResponse.toDomain(code)) },
    )
  }
}
