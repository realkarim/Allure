package com.realkarim.login.domain.usecase

import com.realkarim.domain.model.User
import com.realkarim.domain.result.OutCome
import com.realkarim.domain.usecase.AsyncUseCase
import com.realkarim.login.domain.source.LoginRemote
import javax.inject.Inject

class LoginUseCase @Inject constructor(
  private val loginRemote: LoginRemote,
) : AsyncUseCase<LoginUseCase.Input, User>() {

  override suspend fun run(input: Input): OutCome<User> {
    return loginRemote.login(username = input.username, password = input.password)
  }

  data class Input(val username: String, val password: String)
}
