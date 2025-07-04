package com.realkarim.login.data.source

import com.realkarim.data.result.OutCome
import com.realkarim.login.data.request.LoginRequestBody
import com.realkarim.login.domain.model.User

interface LoginRemote {
  suspend fun login(loginRequestBody: LoginRequestBody): OutCome<User>
}
