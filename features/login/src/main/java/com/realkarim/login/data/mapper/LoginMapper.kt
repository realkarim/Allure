package com.realkarim.login.data.mapper

import com.realkarim.domain.model.User
import com.realkarim.login.data.response.LoginResponse

interface LoginMapper {
  suspend fun toDomain(userResponse: LoginResponse): User
}
