package com.realkarim.login.domain.source

import com.realkarim.domain.model.User
import com.realkarim.domain.result.OutCome

interface LoginRemote {
  suspend fun login(username: String, password: String): OutCome<User>
}
