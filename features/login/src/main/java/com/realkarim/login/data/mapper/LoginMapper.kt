package com.realkarim.login.data.mapper

import com.realkarim.login.data.response.LoginResponse
import com.realkarim.login.domain.model.User

interface LoginMapper {
  suspend fun toDomain(userResponse: LoginResponse): User
}
